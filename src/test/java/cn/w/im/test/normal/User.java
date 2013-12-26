package cn.w.im.test.normal;

/**
 * Creator: JackieHan
 * DateTime: 13-12-23 下午2:38
 */
public class User {

    public enum Gender {MALE, FEMALE};

    public static class Name {

        public static Name Default(){
            Name name=new Name();
            name.setFirst("Jackie");
            name.setLast("Han");
            return name;
        }

        private String _first, _last;

        public String getFirst() {
            return _first;
        }

        public void setFirst(String first) {
            this._first = first;
        }

        public String getLast() {
            return _last;
        }

        public void setLast(String last) {
            this._last = last;
        }
    }

    private Gender gender;
    private Name name;
    private boolean isVerified;
    private byte[] userImage;

    public static User Default(){
        User user=new User();
        user.setName(Name.Default());
        user.setGender(Gender.MALE);
        user.setVerified(true);
        user.setUserImage(new String("Rm9vYmFyIQ==").getBytes());
        return user;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}
