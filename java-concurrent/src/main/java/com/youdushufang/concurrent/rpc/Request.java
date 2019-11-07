package com.youdushufang.concurrent.rpc;

import lombok.Data;

@Data
class Request {

    // 全局唯一的 id
    private Integer id;

    // 简化的请求内容
    private String body;

    static Request newRequest(Integer id, String body) {
        Request message = new Request();
        message.setId(id);
        message.setBody(body);
        return message;
    }
}
