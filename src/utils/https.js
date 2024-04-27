import constant from "./constant";
//-------------------------------------------get----------------------------------------------
async function get(path, body) {
  try {
    const resp = await fetch(constant.localDomain + path, {
      method: "GET",
      body: JSON.stringify(body),
    });
    return resp;
  } catch (error) {
    console.log(error);
    return null;
  }
}
//------------------------------------post----------------------------------------------
async function post(path, body) {
  try {
    const resp = await fetch(constant.localDomain + path, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });
    return resp;
  } catch (error) {
    console.log(error);
    return null;
  }
}
//-------------------------------------------put----------------------------------------------
async function put(path, body) {
  const resp = await fetch(constant.localDomain + path, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return resp;
}
//-------------------------------------------delete----------------------------------------------
async function del(path, body) {
  const resp = await fetch(constant.localDomain + path, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return resp;
}
//-------------------------------------------form put----------------------------------------------
async function putFormData(path, body) {
  const formData = new FormData();
  for (let key in body) {
    formData.append(key, body[key]);
  }
  const resp = await fetch(constant.localDomain + path, {
    method: "PUT",
    body: formData,
  });
  return resp;
}

//-------------------------------------------form post----------------------------------------------
async function postFormData(path, body) {
  const formData = new FormData();
  for (let key in body) {
    formData.append(key, body[key]);
  }
  const resp = await fetch(constant.localDomain + path, {
    method: "POST",
    body: formData,
  });
  return resp;
}
//-------------------------------------------file post----------------------------------------------
async function postMedia(path, file, fileType) {
  let formData = new FormData();
  formData.append("file", file);
  formData.append("fileType", fileType);
  const resp = await fetch(constant.localDomain + path, {
    method: "POST",
    body: formData,
  });
  return resp;
}
//-------------------------------------------file put----------------------------------------------
async function putMedia(path, file, fileType, filePath) {
  let formData = new FormData();
  formData.append("file", file);
  formData.append("fileType", fileType);
  formData.append("filePath", filePath);
  const resp = await fetch(constant.localDomain + path, {
    method: "PUT",
    body: formData,
  });
  return resp;
}

export default {
  get,
  post,
  put,
  del,
  postMedia,
  putMedia,
  postFormData,
  putFormData,
};
