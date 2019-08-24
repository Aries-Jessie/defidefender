package org.bithacks.defidefender.service;

import java.math.BigInteger;

public interface MPCService {

    void KeyGeneration(int bitLengthVal, int certainty);

    void initPaillier();

    BigInteger Encryption(BigInteger m, BigInteger r);

    BigInteger Encryption(BigInteger m);

    BigInteger Decryption(BigInteger c);

    BigInteger cipher_add(BigInteger em1, BigInteger em2);
}
