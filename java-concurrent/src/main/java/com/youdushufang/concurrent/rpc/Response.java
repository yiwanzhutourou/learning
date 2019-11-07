package com.youdushufang.concurrent.rpc;

import lombok.Data;

@Data
class Response {

    private Integer requestId;

    private String result;

    static Response newResponse(Integer requestId, String result) {
        Response response = new Response();
        response.setRequestId(requestId);
        response.setResult(result);
        return response;
    }
}
