package utils;

// Class này dùng để lưu tạm info error khi 1 step bị fail
// để Hooks.tearDown() đọc lại và ghi vào cột Ghi Chú của file TestResults.xlsx
// do đang setup cơ chế chạy song song các testcase => dùng class ThreadLocal để lưu song song các kết quả

public class TestContext {
    private static final ThreadLocal<String> FAILURE_NOTE = new ThreadLocal<>();

    private TestContext(){}

//    function để lưu kết quả lỗi
    public static void setNote(String note){ FAILURE_NOTE.set(note);}

    public static String getNote(){
        String note = FAILURE_NOTE.get();
        return note == null ? "" : note;
    }

    public static void clear(){ FAILURE_NOTE.remove();}
}
