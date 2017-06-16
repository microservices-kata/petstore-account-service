package com.thoughtworks.petstore.contract;

import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

// WHY THIS JAVA FILE:
// stackoverflow.com/questions/10347384/scala-can-i-declare-a-public-field-that-will-not-generate-getters-and-setters-w
public abstract class VerifyPactsTestTarget {

    @TestTarget
    public final Target target = new HttpTarget(8083);

}
