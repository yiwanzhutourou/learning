package com.youdushufang.dp.facade.compiler;

public class EchoParser extends Parser {

    @Override
    void parse(Scanner scanner, ProgramNodeBuilder programNodeBuilder) {
        Token nextToken;
        while ((nextToken = scanner.scan()) != null) {
            programNodeBuilder.newVariable(nextToken.getToken());
        }
    }
}
