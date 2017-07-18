package icom.yh.wish.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import icom.yh.wish.entity.WishSimpleData;
import icom.yh.wish.service.WishService;

@Controller
@RequestMapping("/wish")
public class WishController {

	@Resource
	private WishService wishService;
	
	/**
	 * 搜索产品列表
	 * @param currentPage
	 * @param pageSize
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/wishDataList", method=RequestMethod.GET)
	public String wishDataList(@RequestParam(defaultValue="1") int currentPage, 
			@RequestParam(defaultValue="24")int pageSize, 
			@RequestParam(defaultValue="")String key, /*@RequestParam(defaultValue="")String[] itemIds,*/ Model model){
		model = wishService.wishDataList(currentPage,pageSize,key, /*itemIds,*/ model);
		return "search";
	}
	
	
	@RequestMapping(value="/simpleDataSave" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> simpleDataSave(WishSimpleData simpleData){
		return wishService.simpleDataSave(simpleData);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String wishSearch(String query, int start){
		return "";
	}
	
	/*@RequestMapping(value="/simpleDataDel/{itemId}" ,method=RequestMethod.GET)
	public String simpleDataDel(@PathVariable String itemId, 
			@RequestParam(defaultValue="1")int currentPage,
			@RequestParam(defaultValue="magnets")String key, 
			@RequestParam(defaultValue="search")String t,
			@RequestParam(defaultValue="")String params){
		wishService.simpleDataDel(itemId);
		if(t.equals("select"))
			return "redirect:/wish/selectWishData?currentPage="+currentPage+"&"+params;
		else if(t.equals("search"))
			return "redirect:/wish/wishDataList?currentPage="+currentPage+"&key="+key;
		else
			return "redirect:/wish/add";
	}*/
	
	@RequestMapping(value="/simpleDataDel/{itemId}" ,method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> simpleDataDel(@PathVariable String itemId, String type){
		return wishService.simpleDataDel(itemId, type);
	}
	
	@RequestMapping("/wishDataDownLoad")
	public ResponseEntity<byte[]> wishDataDownLoad(@RequestParam(defaultValue="0")int isC,@RequestParam(defaultValue="1")int type) throws Exception{
		String path= wishService.myWishData(isC, type);
        File file=new File(path);  
        HttpHeaders headers = new HttpHeaders();    
      //  String fileName=new String("wish.xlsx".getBytes("UTF-8"),"iso-8859-1");//Ϊ�˽������������������  
       // String fileName = "wish.xls";
        headers.setContentDispositionFormData("attachment", "wish.zip");   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                          headers, HttpStatus.CREATED);    
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model){
		model = wishService.add(model);
		return "add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@RequestParam(value = "itemIds")String[] itemIds,Model model){
		wishService.myProIdAdd(itemIds, model);
		return "add";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(@RequestParam(defaultValue="1")int type,
			@RequestParam(defaultValue="0")int isC,
			@RequestParam(defaultValue="0")int isS,
			@RequestParam(defaultValue="1")int currentPage, 
			@RequestParam(defaultValue="25")int pageSize,Model model){
		model = wishService.myWishDataList(type,isC, isS, currentPage, pageSize, model);
		return "list";
	}
	
	@RequestMapping(value="/proDel/{itemId}/{currentPage}", method=RequestMethod.GET)
	public String proDel(@PathVariable String itemId, @PathVariable int currentPage){
		wishService.simpleDataDel(itemId);
		return "redirect:/wish/list?currentPage="+currentPage;
	}
	
	@RequestMapping(value="/proDelAll", method=RequestMethod.GET)
	public String proDelAll(){
		wishService.simpleDataDelAll();
		return "redirect:/wish/list?currentPage=1";
	}
	
	@RequestMapping(value="/selectWishData", method=RequestMethod.GET)
	public String selectWishData(@RequestParam(defaultValue="1") int hot,
			@RequestParam(defaultValue="0")int fly,
			@RequestParam(defaultValue="0")int newhot,
			@RequestParam(defaultValue="0")int newfly,
			@RequestParam(defaultValue="0")int newshophot,
			@RequestParam(defaultValue="0")int newshopfly,
			@RequestParam(defaultValue="1")int currentPage,
			@RequestParam(defaultValue="25")int pageSize,Model model){
		model = wishService.selectWishData(hot,fly,newhot,newfly,newshophot,newshopfly,currentPage,pageSize,model);
		return "select";
	}
	
	/**
	 * 归档
	 * @param itemId
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value="/dataC/{itemId}/{currentPage}")
	public String dataCatch(@PathVariable String itemId, @PathVariable int currentPage){
		wishService.dataC(itemId);
		return "redirect:/wish/list?currentPage="+currentPage;
	}
	
	@RequestMapping(value="/dataCAll")
	public String dataCAll(){
		wishService.dataCAll();
		return "redirect:/wish/list?currentPage=1";
	}
	
	@RequestMapping(value="/keyWordsTask", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> keyWordsTask(@RequestParam(value="keyWords[]", defaultValue="")String[] keyWords,@RequestParam(defaultValue="2")int num){
		return wishService.keyWordsTask(keyWords, num);
	}
	
	@RequestMapping(value="/keyWordsTask", method=RequestMethod.GET)
	public String _keyWordsTask(){
		return "keywordstask";
	}
	
	@RequestMapping(value="/taskList", method=RequestMethod.GET)
	public String taskList(@RequestParam(defaultValue="1")int currentPage, 
			@RequestParam(defaultValue="20")int pageSize, Model model){
		model = wishService.taskList(currentPage, pageSize, model);
		return "tasklist";
	}
	
	@RequestMapping(value="/taskDataList/{taskId}", method=RequestMethod.GET)
	public String taskDataList(@PathVariable int taskId,
			@RequestParam(defaultValue="1")int currentPage,
			@RequestParam(defaultValue="30")int pageSize, Model model){
		model = wishService.taskDataList(taskId, currentPage, pageSize, model);
		return "taskdatalist";
	}
	
	@RequestMapping(value="/taskDel/{sdId}/{taskId}/{currentPage}", method=RequestMethod.GET)
	public String taskDel(@PathVariable int sdId,@PathVariable int taskId,@PathVariable int currentPage){
		wishService.taskDel(sdId, taskId);
		return "redirect:/wish/taskDataList/"+taskId+"?currentPage="+currentPage;
	}
	
	@RequestMapping(value="/listAdd/{sdId}/{taskId}/{currentPage}")
	public String listAdd(@PathVariable int sdId,@PathVariable int taskId, @PathVariable int currentPage){
		wishService.listAdd(sdId);
		return "redirect:/wish/taskDataList/"+taskId+"?currentPage="+currentPage;
	}
	
}
