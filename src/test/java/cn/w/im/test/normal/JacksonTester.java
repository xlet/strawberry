package cn.w.im.test.normal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Creator: JackieHan
 * DateTime: 13-12-23 下午2:27
 */
@RunWith(JUnit4.class)
public class JacksonTester {

    private ObjectMapper mapper=new ObjectMapper();

    @Test
    public void Test_Json_Serializer_POJO() throws Exception {
        User user=User.Default();
        String jsonStr =mapper.writeValueAsString(user);
        System.out.println(jsonStr);
    }
}



