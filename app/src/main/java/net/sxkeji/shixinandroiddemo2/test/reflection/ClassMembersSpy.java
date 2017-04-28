package net.sxkeji.shixinandroiddemo2.test.reflection;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/1/14.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

enum ClassMember {
    CONSTRUCTOR,
    FIELD,
    METHOD,
    CLASS,
    ALL
}

public class ClassMembersSpy extends BaseTestClass {
    public String mClassName = "ClassMembersSpy";

    private final static String CLASS_NAME = "java.lang.String";
    private final static String CLASS_NAME_1 = "net.sxkeji.shixinandroiddemo2.test.reflection.ClassMembersSpy";
    private final static String CLASS_NAME_MEMBER = "java.lang.reflect.Member";
    private final static String CLASS_NAME_FIELD = "java.lang.reflect.Field";
    private final static String[] MEMBER_NAME = {"ALL"};

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName(CLASS_NAME_FIELD);
            printFormat("Class：\n %s\n\n", aClass.getCanonicalName());
//            ClassMembersSpy membersSpy = (ClassMembersSpy) aClass.newInstance();
//            membersSpy.test();

            Package aPackage = aClass.getPackage();
            printFormat("Package：\n %s\n\n", (aPackage != null ? aPackage.getName() : "--  No Package ---"));

            for (String classMember : MEMBER_NAME) {

                switch (ClassMember.valueOf(classMember)) {
                    case CONSTRUCTOR:
                        printMembers(aClass.getConstructors(), "Constructor");
                        break;
                    case FIELD:
                        printMembers(aClass.getFields(), "Fields");
                        break;
                    case METHOD:
                        printMembers(aClass.getMethods(), "Methods");
                        break;
                    case CLASS:
                        printClasses(aClass);
                        break;
                    case ALL:
                        printMembers(aClass.getConstructors(), "Constructor");
                        printMembers(aClass.getFields(), "Fields");
                        printMembers(aClass.getMethods(), "Methods");
                        printClasses(aClass);
                        break;
                    default:
                        assert false;
                }

            }
        } catch (ClassNotFoundException e) {
            printFormat("ClassNotFoundException " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void test(){

    }

}

