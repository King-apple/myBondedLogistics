import request from "@/router/axios";

export function fileSave(fileVal) {
  return request({
    url: "/baoshuisystem/sys-file/fileSave",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: fileVal
  });
}

export function getFileInfo(map) {
  return request({
    url: "/baoshuisystem/sys-file/getSysFile",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: map
  });
}

export function delFile(id) {
  return request({
    url: "/baoshuisystem/sys-file/" + id,
    method: "delete"
  });
}

export function handleDown(filename, bucket) {
  return request({
    url: "/baoshuisystem/sys-file/" + bucket + "/" + filename,
    method: "get",
    responseType: "blob"
  }).then(response => {
    // 处理返回的文件流
    const blob = response.data;
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}

export function delSysFile(map) {
  return request({
    url: "/baoshuisystem/sys-file/delSysFile",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: map
  });
}
