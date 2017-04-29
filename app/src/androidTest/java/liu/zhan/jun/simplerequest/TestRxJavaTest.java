package liu.zhan.jun.simplerequest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by 刘展俊 on 2017/4/22.
 */
@RunWith(AndroidJUnit4.class)
public class TestRxJavaTest {
    @Test
    public void rxjavaFrow1() throws Exception {

    }

    private TestRxJava java;

    @Before
    public void testNewJava(){
        java=new TestRxJava();
    }
    @Test
    public void rxjava() throws Exception {

       assertEquals(0,java.rxjava());

    }

    @Test
    public void testGetstring() throws Exception{
        assertEquals("bb",java.getString());
    }

    @Test
    public void testrequet()throws Exception{
       assertEquals(0,java.request());
    }
    @Test
    public void rxjavaFrow(){
        assertEquals(0,java.rxjavaFrow());
    }

}