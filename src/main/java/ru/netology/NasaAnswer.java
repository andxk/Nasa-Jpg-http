package ru.netology;

public class NasaAnswer {
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NasaAnswer{" +
                "\n copyright='" + copyright + '\'' +
                ",\n date='" + date + '\'' +
                ",\n explanation='" + explanation + '\'' +
                ",\n hdurl='" + hdurl + '\'' +
                ",\n media_type='" + media_type + '\'' +
                ",\n service_version='" + service_version + '\'' +
                ",\n title='" + title + '\'' +
                ",\n url='" + url + '\'' +
                '}';
    }
}
