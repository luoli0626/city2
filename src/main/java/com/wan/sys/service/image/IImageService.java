package com.wan.sys.service.image;

import com.wan.sys.entity.image.Image;

import java.util.List;

public interface IImageService {

    void addImages(List<Image> images);

    List<Image> getList(Image image);
}
