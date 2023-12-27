package app.file.entity;

/**
 * Description：文件信息
 * <p>Date: 2023/11/21
 * <p>Time: 22:31
 *
 * @Author SillyBaka
 **/
public class File {

    /**
     * 文件id
     */
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件存储路径
     */
    private String url;

    /**
     * 是否本地存储，0-否，1-是
     */
    private Integer isLocal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
        this.isLocal = isLocal;
    }
}
