package dtos;

public class OurDTO {
    private String joke1;
    private String joke1Reference;
    private String joke2;
    private String joke2Reference;

    public OurDTO(ChuckDTO cDTO, DadDTO dDTO) {
        this.joke1 = cDTO.getValue();
        this.joke1Reference = cDTO.getUrl();
        this.joke2 = dDTO.getJoke();
        this.joke2Reference = dDTO.getUrl();
    }

    public String getJoke1() {
        return joke1;
    }

    public void setJoke1(String joke1) {
        this.joke1 = joke1;
    }

    public String getJoke1Reference() {
        return joke1Reference;
    }

    public void setJoke1Reference(String joke1Reference) {
        this.joke1Reference = joke1Reference;
    }

    public String getJoke2() {
        return joke2;
    }

    public void setJoke2(String joke2) {
        this.joke2 = joke2;
    }

    public String getJoke2Reference() {
        return joke2Reference;
    }

    public void setJoke2Reference(String joke2Reference) {
        this.joke2Reference = joke2Reference;
    }
    
    
}
