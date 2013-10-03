package net.healthroad.harmful.util;

public interface ICommonCodes {

    /** 검색화면 값 전달용 요청 코드 */
    int SEARCH_REQ_CODE = 1000;
    /** 검색화면에서 전달하는 번들 키 */
    String SEARCH_BUNDLE_KEY = "search_key";
    /** 검색화면에서 전달하는 번들 키 중 타입 */
    String SEARCH_BUNDLE_TYPE_KEY = "search_type_key";
    /** 검색어 타입 중 검색어 입력 */
    String SEARCH_TYPE_PHASE = "P";
    /** 검색어 타입 중 버튼 입력 */
    String SEARCH_TYPE_BUTTON = "B";
}
