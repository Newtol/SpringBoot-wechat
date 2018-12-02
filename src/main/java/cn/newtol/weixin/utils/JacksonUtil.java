package cn.newtol.weixin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: 公众号：Newtol
 * @Description: Jackson 转换工具类
 * @Date: Created in 23:14 2018/11/28
 */
@Service
public class JacksonUtil {
    public String getXmlFromBean(Object object) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String result = xmlMapper.writeValueAsString(object);
        return result;
    }
}
