package ru.gt2.gar.parse.rest;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "WebServices/Public")
public interface FileInfoService {
    @GetExchange(url = "GetLastDownloadFileInfo")
    FileInfo getLast();

    @GetExchange(url = "GetAllDownloadFileInfo")
    List<FileInfo> getAll();
}
