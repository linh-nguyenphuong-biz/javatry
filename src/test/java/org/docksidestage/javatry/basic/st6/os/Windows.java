package org.docksidestage.javatry.basic.st6.os;

public class Windows extends St6OperationSystem {
    public Windows(String loginId) {
        super(OS_TYPE_WINDOWS, loginId);
    }

    @Override
    public String getFileSeparator(){
        return "\\";
    }

    @Override
    public String getUserDirectory(){
        return "/Users/" + super.getLoginId();
    }
}
