package org.xlet.strawberry.core.test;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.member.MemberSourceType;
import org.xlet.strawberry.core.member.SexType;
import org.xlet.strawberry.core.member.TempMember;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Test
    public void test_move_position() {
        long a = (long) (192. * 256 * 256 * 256 + 168 * 256 * 256 + 40 * 256 + 18);
        System.out.println(a);

        long c = 192L;
        long d = 168L;

        long b = (192L << 24) + (168L << 16) + (40L << 8) + 18L;
        System.out.println(b);

        assertThat(a).isEqualTo(b);
    }

    @Test
    public void test_hash_code() {
        TempMember tempMember = new TempMember();
        tempMember.setSource("xlet.cn");
        tempMember.setAddress("望海路37号4楼");
        tempMember.setEmail("hanjiayou@xlet.cn");
        tempMember.setId("wdemo1:w094");
        tempMember.setMemberSource(MemberSourceType.OA);
        tempMember.setMobile("18626877807");
        tempMember.setNickname("Jackie");
        tempMember.setPicUrl("http://www.xlet.cn/xxxxxxxxx.jgp");
        tempMember.setSex(SexType.Male);
        tempMember.setSignature("我靠！");
        tempMember.setTelephone("00000000");

        String hashCode1 = md5Hex(tempMember.toString());
        tempMember.setSource("aap.xlet.cn");
        String hashCode2 = md5Hex(tempMember.toString());

        System.out.println(hashCode1);
        System.out.println(hashCode2);

        tempMember = new TempMember();
        String hashCode3 = md5Hex(tempMember.toString());
        System.out.println(hashCode3);
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return message;
    }
}
