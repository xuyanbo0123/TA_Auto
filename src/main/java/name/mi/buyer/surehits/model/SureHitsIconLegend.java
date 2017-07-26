package name.mi.buyer.surehits.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SureHitsIconLegend {
    private String mIconLegendImg;
    private String mIconText;
    private Boolean mIconShowText;
    private Boolean mIsVisible;

    public void setIconLegendImg(String iIconLegendImg) {
        mIconLegendImg = iIconLegendImg;
    }

    public void setIconText(String iIconText) {
        mIconText = iIconText;
    }

    public void setIconShowText(Boolean iIconShowText) {
        mIconShowText = iIconShowText;
    }

    public void setIsVisible(Boolean iIsVisible) {
        mIsVisible = iIsVisible;
    }
}
