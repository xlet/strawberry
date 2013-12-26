package cn.w.imserver.test;

import java.io.IOException;

/**
 * Creator: JackieHan
 * DateTime: 13-12-23 下午5:27
 */
public class InputMasking {

    public String getPassword(String initial) throws IOException{
        MaskingThread listeningThread=new MaskingThread(initial);
        Thread thread=new Thread(listeningThread);
        String password="";
        thread.start();

        while (true){
            char input=(char)System.in.read();
            listeningThread.stopMasking();
            if(input=='\r'){
                input=(char)System.in.read();
                if(input=='\n')
                    break;
                else
                    continue;
            }
            else if(input=='\n'){
                break;
            }
            else{
                password+=input;
            }
        }
        return password;
    }
}
