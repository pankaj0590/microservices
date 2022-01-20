package com.photoapp.api.users.inter.service.call;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.photoapp.api.users.ui.model.AlbumResponseModel;

import feign.FeignException;
import lombok.extern.apachecommons.CommonsLog;

//@FeignClient(name = "albums-service", fallback = AlbumsFallback.class)
@FeignClient(name = "albums-service", fallbackFactory =  AlbumsFallbackFactory.class)
public interface AlbumServiceFeign {
	
	@GetMapping("/albums/user/{id}/albums")
	public List<AlbumResponseModel> getAlbumsById(@PathVariable int id);

}
/*@Component
class AlbumsFallback implements AlbumServiceFeign{

	@Override
	public List<AlbumResponseModel> getAlbumsById(int id) {
		// TODO Auto-generated method stub
		return new ArrayList<AlbumResponseModel>();
	}
	
}*/

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceFeign>{

	@Override
	public AlbumServiceFeign create(Throwable cause) {
		// TODO Auto-generated method stub
		return new AlbumServiceFeignFallback(cause);
	}
	
}

class AlbumServiceFeignFallback implements AlbumServiceFeign{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	 private Throwable cause;
	AlbumServiceFeignFallback(Throwable cause){
		this.cause=cause;
	}
	@Override
	public List<AlbumResponseModel> getAlbumsById(int id) {
		if(cause instanceof FeignException && ((FeignException)cause).status()==404) {
			logger.error("404 took place while calling getAlbumsById by Id: " 
		+ id + ". Error message: " + cause.getLocalizedMessage());
		}else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return new ArrayList<AlbumResponseModel>();
	}
	
}
