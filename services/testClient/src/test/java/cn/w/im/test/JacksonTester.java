package cn.w.im.test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 *
 */
public class JacksonTester {
    /**
     * Polymorphic base class - existing property as simple property on subclasses
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type",
            visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Apple.class, name = "apple"),
            @JsonSubTypes.Type(value = Orange.class, name = "orange")
    })
    static abstract class Fruit {
        public String name;

        protected Fruit(String n) {
            name = n;
        }
    }

    @JsonTypeName("apple")
    static class Apple extends Fruit {
        public int seedCount;
        public String type;

        private Apple() {
            super(null);
            ;
        }

        public Apple(String name, int b) {
            super(name);
            seedCount = b;
            type = "apple";
        }
    }

    @JsonTypeName("orange")
    static class Orange extends Fruit {
        public String color;
        public String type;

        private Orange() {
            super(null);
        }

        public Orange(String name, String c) {
            super(name);
            color = c;
            type = "orange";
        }
    }

    @Test
    public void test_existing_property() throws JsonProcessingException {
        Apple pinguo = new Apple("Apple-A-Day", 16);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pinguo);
        System.out.println(jsonStr);
    }
}
