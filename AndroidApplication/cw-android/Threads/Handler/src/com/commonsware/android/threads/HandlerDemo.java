/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain	a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
	
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package com.commonsware.android.threads;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerDemo extends Activity {
  ProgressBar bar;
  Handler handler=new Handler() {
    @Override
    public void handleMessage(Message msg) {
      bar.incrementProgressBy(msg.arg1);
    }
  };
  AtomicBoolean isRunning=new AtomicBoolean(false);
  
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.main);
    bar=(ProgressBar)findViewById(R.id.progress);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    bar.setProgress(0);
    
    Thread background=new Thread(new Runnable() {
      public void run() {
        try {
          for (int i=0;i<20 && isRunning.get();i++) {
            Thread.sleep(1000);
            
            Message msg=handler.obtainMessage();
            
            msg.arg1=5;
            msg.sendToTarget();
          }
        }
        catch (Throwable t) {
          // just end the background thread
        }
      }
    });
    
    isRunning.set(true);
    background.start();
  }
  
  @Override
  public void onPause() {
    isRunning.set(false);
    super.onPause();
  }
}
