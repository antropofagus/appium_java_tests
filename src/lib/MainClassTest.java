package lib;

import EX0.MainClass;
import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetLocalNumber() {
        int expected_value = 14;
        int actual_value = getLocalNumber();
        Assert.assertEquals("getLocalNumber returns unexpected value",expected_value, actual_value);
    }
}
