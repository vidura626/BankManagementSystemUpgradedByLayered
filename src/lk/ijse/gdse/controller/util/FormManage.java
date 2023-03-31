package lk.ijse.gdse.controller.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FormManage {
    private static HashMap regexList =new HashMap();

    static {
        regexList.put("Name","^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$" );
        regexList.put("Address","[A-Za-z0-9'\\.\\-\\s\\,]{2,}");
        regexList.put("Nic","^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
        regexList.put("Contact","^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
        regexList.put("Email","^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
        regexList.put("Amount","^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$");
        regexList.put("Date","^\\d{4}-\\d{2}-\\d{2}$");
        regexList.put("AccountNumber","^([0-9]{14})$");
        regexList.put("Description","^(.|\\s)*[a-zA-Z0-9]+(.|\\s)*$");
    }
    public static boolean check(RegexTypes types,String text){
        String regex = null;
        switch (types){
            case DESCRIPTION:
                regex = regexList.get("Description").toString();
                break;
            case ACCOUNT_NUMBER:
                regex=regexList.get("AccountNumber").toString();
                break;
            case DATE:
                regex=regexList.get("Date").toString();
                break;
            case AMOUNT:
                regex=regexList.get("Amount").toString();
                break;
            case NIC:
                regex=regexList.get("Nic").toString();
                break;
            case NAME:
                regex=regexList.get("Name").toString();
                break;
            case EMAIL:
                regex=regexList.get("Email").toString();
                break;
            case ADDRESS:
                regex=regexList.get("Address").toString();
                break;
            case CONTACT:
                regex=regexList.get("Contact").toString();
                break;
            default:
                return false;
        }
        return Pattern.compile(regex).matcher(text).matches();
    }

    public static boolean validate(ArrayList<JFXTextField> arrayList,RegexTypes...types){
        for (int i = 0; i < types.length; i++) {
            if(!check(types[i],arrayList.get(i).getText())&&!arrayList.get(i).getText().isEmpty()){
                arrayList.get(i).setFocusColor(Paint.valueOf("RED"));
                arrayList.get(i).requestFocus();
                return false;
            }
        }
        return true;
    }

}
