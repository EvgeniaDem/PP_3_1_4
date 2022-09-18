package bootstrap.exception_handling;


// добавила к 3.1.4 - но метод не прописала
// класс, ответственный за JSON, отображаемый при возникновении исключений (нечто информативное)
// т.е. объект этого класса будет преобразовываться в JSON и выводиться на экран
public class UserIncorrectData {
    private String info;

    public UserIncorrectData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
