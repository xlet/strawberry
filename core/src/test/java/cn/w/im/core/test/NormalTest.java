package cn.w.im.core.test;

import cn.w.im.core.MessageClientType;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * normal function test.
 */
public class NormalTest {

    @Test
    public void test_enum_collection_contains() {
        Collection<MessageClientType> clientTypes = new CopyOnWriteArrayList<MessageClientType>();

        clientTypes.add(MessageClientType.Mobile);
        clientTypes.add(MessageClientType.Web);
        clientTypes.add(MessageClientType.WinForm);

        assertThat(clientTypes.contains(MessageClientType.Mobile)).isEqualTo(true);

    }
}
