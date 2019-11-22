package com.youdushufang;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class CharacterTest {

    @Test
    void testCharacterBytes() {
        String[] characterToTest = new String[] {
                "1",
                "a",
                "汉",
                "𠮷",
                "🙄"
        };
        Charset[] charsets = new Charset[] {
                StandardCharsets.UTF_8,
                StandardCharsets.UTF_16,
                StandardCharsets.UTF_16BE,
                StandardCharsets.UTF_16LE
        };
        for (String character : characterToTest) {
            for (Charset charset : charsets) {
                System.out.println(character + " in "
                        + charset + " contains "
                        + countBytes(character, charset)
                        + " bytes");
            }
        }
    }

    private int countBytes(String character, Charset charset) {
        return character.getBytes(charset).length;
    }
}
