package cc.oit.bsmes.common.listener;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.interfaceWWAc.service.EquipParamAcquisitionClient;
import cc.oit.bsmes.opc.client.OpcClient;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chanedi on 14-3-11.
 */
public class ContextListener implements ServletContextListener {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private Timer timer = new Timer();
	private static String NEED_DA_PARM = "needDA";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if ("true".equalsIgnoreCase(sce.getServletContext().getInitParameter(
				NEED_DA_PARM))) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					EquipParamAcquisitionClient client = (EquipParamAcquisitionClient) ContextUtils
							.getBean(EquipParamAcquisitionClient.class);
					// 开始自动采集数据 并且初始化 jeasyOpc
					try {
						OpcClient.initOpcServerConnection();
						client.autoCollectDatas();
						
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}

			};
			timer.schedule(task, DateUtils.addSeconds(new Date(), 10), 60 * 1000);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {	
		timer.cancel();
		// 关闭
		OpcClient.server.disconnect();
	}

}
