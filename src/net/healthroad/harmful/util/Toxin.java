package net.healthroad.harmful.util;

public class Toxin {

    /** 인덱스 */
    private int idx;
    /** 한글명 */
    private String kor;
    /** 영문명 */
    private String eng;
    /** 키워드 */
    private String keyword;
    /** 내용 */
    private String contents;

    /**
     * 생성자
     * @param idx 인덱스
     * @param kor 한글명
     * @param eng 영문명
     * @param keyword 키워드
     * @param contents 내용
     */
    public Toxin(int idx, String kor, String eng, String keyword, String contents) {
        this.idx = idx;
        this.kor = kor;
        this.eng = eng;
        this.keyword = keyword;
        this.contents = contents;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Toxin{" +
                "idx=" + idx +
                ", kor='" + kor + '\'' +
                ", eng='" + eng + '\'' +
                ", keyword='" + keyword + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
