package net.sxkeji.shixinandroiddemo2.test.designpattern;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/11/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class PersonTest {
    private final String mName;
    private int mAge;
    private String mLocation;

    private PersonTest(Builder builder) {
        mName = builder.mName;
        mAge = builder.mAge;
        mLocation = builder.mLocation;
    }

    public static final class Builder {
        private String mName;
        private int mAge;
        private String mLocation;

        public Builder() {
        }

        public Builder mName(String mName) {
            this.mName = mName;
            return this;
        }

        public Builder mAge(int mAge) {
            this.mAge = mAge;
            return this;
        }

        public Builder mLocation(String mLocation) {
            this.mLocation = mLocation;
            return this;
        }

        public PersonTest build() {
            return new PersonTest(this);
        }
    }
}
