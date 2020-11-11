package org.docksidestage.javatry.basic.st6.os;

public class Mac extends St6OperationSystem{

    public Mac(String loginId) {
        super(OS_TYPE_MAC, loginId);
    }

    @Override
    public String getFileSeparator(){
        return "/";
    }

    @Override
    public String getUserDirectory(){
        return "/Users/" + super.getLoginId();
    }

}
