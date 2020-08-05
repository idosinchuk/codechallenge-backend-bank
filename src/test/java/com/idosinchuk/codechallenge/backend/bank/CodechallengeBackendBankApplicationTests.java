package com.idosinchuk.codechallenge.backend.bank;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CodechallengeBackendBankApplication.class})
class CodechallengeBackendBankApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void applicationContextTest() {
        CodechallengeBackendBankApplication.main(new String[] {});
    }

}
