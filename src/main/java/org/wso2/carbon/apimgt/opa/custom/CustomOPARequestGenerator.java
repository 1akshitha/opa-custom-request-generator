package org.wso2.carbon.apimgt.opa.custom;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.opa.OPAConstants;
import org.apache.synapse.mediators.opa.OPARequestGenerator;
import org.apache.synapse.mediators.opa.OPASecurityException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class CustomOPARequestGenerator implements OPARequestGenerator {

    @Override
    public String generateRequest(String policyName, String rule, Map<String, String> additionalParameters,
                                  MessageContext messageContext) throws OPASecurityException {

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        TreeMap<String, String> transportHeadersMap = (TreeMap<String, String>) axis2MessageContext
                .getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

        JSONObject inputObject = new JSONObject();
        JSONObject opaPayload = new JSONObject();
        opaPayload.put(OPAConstants.REQUEST_TRANSPORT_HEADERS_KEY, new JSONObject(transportHeadersMap));
        inputObject.put(OPAConstants.INPUT_KEY, opaPayload);
        return inputObject.toString();
    }

    @Override
    public boolean handleResponse(String policyName, String rule, String opaResponse,
                                  Map<String, String> additionalParameters,
                                  MessageContext messageContext) throws OPASecurityException {

        JSONObject response = new JSONObject(opaResponse);
        try {
            return response.getBoolean("result");
        } catch (JSONException e) {
            throw new OPASecurityException(OPASecurityException.INTERNAL_ERROR,
                    OPASecurityException.INTERNAL_ERROR_MESSAGE, e);
        }
    }
}
