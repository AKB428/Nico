package akb428.twitter.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import akb428.twitter.model.videoinfo.Variant;

public class VideoInfo {

	private Long durationMillis = 0L;
	private List<Variant> variants = null;
	private List<Long> aspectRatio = null;

	public static VideoInfo fromRawJson(String rawJsonString) throws JsonProcessingException, IOException {
		VideoInfo videoInfo = new VideoInfo();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(rawJsonString);

		JsonNode mediaNodo  = rootNode.get("extended_entities").get("media");
		//videoNode.size();
		
		// 方法2:オブジェクトのフィールド名からフィールドを取得
	    Iterator<String> fieldNames = mediaNodo.get(0).getFieldNames();
	    while (fieldNames.hasNext()) {
	        String name = fieldNames.next();
	        String value = rootNode.path(name).toString();

	        System.out.println(name + ":" + value);
	    }
		
		JsonNode videoNode = mediaNodo.get(0).get("video_info");
		videoInfo.setDurationMillis(videoNode.get("duration_millis").getLongValue());
		JsonNode aspectRatioNode = videoNode.get("aspect_ratio");
		
		if (aspectRatioNode != null) {
			List<Long> aspectRatioList = new ArrayList<>();
			Iterator<JsonNode> aspectRatioChildNodes = aspectRatioNode.getElements();
		    while (aspectRatioChildNodes.hasNext()) {
		    	aspectRatioList.add(aspectRatioChildNodes.next().getLongValue());
		    }
			videoInfo.setAspectRatio(aspectRatioList);
		}
		
		JsonNode variantsNode = videoNode.get("variants");
		
		if (variantsNode != null) {
			List<Variant> variantList = new ArrayList<>();
			Iterator<JsonNode> variantChildNodes = variantsNode.getElements();
		    while (variantChildNodes.hasNext()) {
		    	Variant variant = new Variant();
		    	JsonNode child = variantChildNodes.next();
		    	if (child.get("bitrate") != null){ 
		    	variant.setBitrate(child.get("bitrate").getLongValue());
		    	}
		    	variant.setContentType(child.get("content_type").getTextValue());
		    	variant.setUrl(child.get("url").getTextValue());
		    	variantList.add(variant);
		    }
			videoInfo.setVariants(variantList);
		}
		
		return videoInfo;
	}

	public List<String> getVideoUrls() {
		List<String> videoUrls = new ArrayList<String>();
		return videoUrls;
	}
	
	public long getDurationMillis() {
		return durationMillis;
	}

	public void setDurationMillis(long durationMillis) {
		this.durationMillis = durationMillis;
	}

	public List<Variant> getVariants() {
		return variants;
	}

	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}

	public List<Long> getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(List<Long> aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
}
