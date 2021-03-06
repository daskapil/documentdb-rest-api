certsdir=/opt/certs
mkdir -p ${certsdir}

truststore=${certsdir}/rds-truststore.jks
storepassword=password

curl -sS "https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem" > ${certsdir}/rds-combined-ca-bundle.pem
awk 'split_after == 1 {n++;split_after=0} /-----END CERTIFICATE-----/ {split_after=1}{print > "rds-ca-" n ".pem"}' < ${certsdir}/rds-combined-ca-bundle.pem

for CERT in rds-ca-*; do
  alias=$(openssl x509 -noout -text -in "$CERT" | perl -ne 'next unless /Subject:/; s/.*(CN=|CN = )//; print')
  echo "Importing $alias"
  keytool -import -file "${CERT}" -alias "${alias}" -storepass ${storepassword} -keystore ${truststore} -noprompt
  rm "$CERT"
done

rm ${certsdir}/rds-combined-ca-bundle.pem

echo "Trust store content is: "

# shellcheck disable=SC2162
keytool -list -v -keystore "$truststore" -storepass ${storepassword} | grep Alias | cut -d " " -f3- | while read alias
do
   # shellcheck disable=SC2006
   expiry=`keytool -list -v -keystore "$truststore" -storepass ${storepassword} -alias "${alias}" | grep Valid | perl -ne 'if(/until: (.*?)\n/) { print "$1\n"; }'`
   echo " Certificate ${alias} expires in '$expiry'"
done