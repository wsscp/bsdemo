package cc.oit.bsmes.interfaceWebService;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "MesService", targetNamespace = "http://webservice.erpds.cmpsoft.com/")
public interface MesService  {
	public String saveJJD(@WebParam(name="jjdBeans")String jjdBeans) throws Exception;
	
	public String saveCGSQ(@WebParam(name="cgsqBeans")String cgsqBeans) throws Exception;
}
