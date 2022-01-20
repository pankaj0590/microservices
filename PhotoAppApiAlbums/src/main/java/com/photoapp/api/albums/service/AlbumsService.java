package com.photoapp.api.albums.service;

import java.util.List;

import com.photoapp.api.albums.entity.AlbumEntity;

public interface AlbumsService {
	List<AlbumEntity> getAlbums(int id);
}
