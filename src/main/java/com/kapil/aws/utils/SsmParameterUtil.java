package com.kapil.aws.utils;

import com.kapil.aws.DocDBRestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SsmParameterUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsmParameterUtil.class);

    private SsmParameterUtil() {
    }

    public static String getParaValue(SsmClient ssmClient, String paraName) {
        return getParaValue(ssmClient, paraName, false);
    }

    public static String getParaValue(SsmClient ssmClient, String paraName, boolean withDecryption) {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paraName)
                    .withDecryption(withDecryption)
                    .build();
            return ssmClient
                    .getParameter(parameterRequest)
                    .parameter()
                    .value();
        } catch (SsmException e) {
            throw new DocDBRestApiException(String.format("Error while getting parameter value of %s from SSM Parameter Store", paraName), e);
        }
    }
}