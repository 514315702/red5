package org.red5.onlinelive;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;

import java.text.SimpleDateFormat;
import java.util.*;

public class Application extends ApplicationAdapter  implements IStreamAwareScopeHandler {

    private IScope appScope;

    private IServerStream serverStream;

    /** {@inheritDoc} */
    @Override
    public boolean appStart(IScope app) {
        super.appStart(app);
        log.info("oflaDemo appStart");
        System.out.println("oflaDemo appStart");
        appScope = app;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean appConnect(IConnection conn, Object[] params) {
        log.info("oflaDemo appConnect");
        IScope appScope = conn.getScope();
        log.debug("App connect called for scope: {}", appScope.getName());
        // getting client parameters
        Map<String, Object> properties = conn.getConnectParams();
        if (log.isDebugEnabled()) {
            for (Map.Entry<String, Object> e : properties.entrySet()) {
                log.debug("Connection property: {} = {}", e.getKey(), e.getValue());
            }
        }
        // Trigger calling of "onBWDone", required for some FLV players
        // commenting out the bandwidth code as it is replaced by the mina filters
        //      measureBandwidth(conn);
        //		if (conn instanceof IStreamCapableConnection) {
        //			IStreamCapableConnection streamConn = (IStreamCapableConnection) conn;
        //			SimpleConnectionBWConfig bwConfig = new SimpleConnectionBWConfig();
        //			bwConfig.getChannelBandwidth()[IBandwidthConfigure.OVERALL_CHANNEL] =
        //				1024 * 1024;
        //			bwConfig.getChannelInitialBurst()[IBandwidthConfigure.OVERALL_CHANNEL] =
        //				128 * 1024;
        //			streamConn.setBandwidthConfigure(bwConfig);
        //		}

        //		if (appScope == conn.getScope()) {
        //			serverStream = StreamUtils.createServerStream(appScope, "live0");
        //			SimplePlayItem item = new SimplePlayItem();
        //			item.setStart(0);
        //			item.setLength(10000);
        //			item.setName("on2_flash8_w_audio");
        //			serverStream.addItem(item);
        //			item = new SimplePlayItem();
        //			item.setStart(20000);
        //			item.setLength(10000);
        //			item.setName("on2_flash8_w_audio");
        //			serverStream.addItem(item);
        //			serverStream.start();
        //			try {
        //				serverStream.saveAs("aaa", false);
        //				serverStream.saveAs("bbb", false);
        //			} catch (Exception e) {}
        //		}

        return super.appConnect(conn, params);
    }

    /** {@inheritDoc} */
    @Override
    public void appDisconnect(IConnection conn) {
        log.info("oflaDemo appDisconnect");
        if (appScope == conn.getScope() && serverStream != null) {
            serverStream.close();
        }
        super.appDisconnect(conn);
    }


    @Override
    public void streamPublishStart(IBroadcastStream stream) {
        if(true)
        {
            try
            {
                //推过来的名字，无人机编号（名字）
                String streamName = stream.getPublishedName();
                //查询到未结束的事件的，将所有未结束的事件都保存视频
                Date startDate = new Date();
                /**
                 *
                 List<String> ids = combatTrainingMapper.findListId(new CombatTraining());
                 if(ids== null || ids.size() == 0){
                 return;
                 }
                 */
                //如果此时不存在，则直接返回，不记录
                String fileName =streamName+formatDate(new Date())+ UUID.randomUUID().toString();
                stream.saveAs(fileName, true);
                //保存数据库
                /*RedBlueVideoUrl redBlueVideoUrl=   new RedBlueVideoUrl();
                redBlueVideoUrl.setVideoUrl(fileName);
                redBlueVideoUrl.setStartDate(startDate);
                redBlueVideoUrl.setEndTime(new Date());
                redBlueVideoUrlService.insertRedBlueVideoUrl(redBlueVideoUrl);*/
                //此处有设备唯一号，视频地址，再就是事件ID,事件类型，id，同时开始的事件，公用一个视频
            }
            catch (Exception  e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * 文件传输格式
     * @param date
     * @return
     */
    private  static  String formatDate(Date date) {
        SimpleDateFormat formatter;
        String pattern = "/yyyy/MM/dd/";
        Locale locale = new Locale("en", "US");
        formatter = new SimpleDateFormat(pattern, locale);
        return formatter.format(date);
    }

}
