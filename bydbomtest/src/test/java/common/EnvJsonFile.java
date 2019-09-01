package common;

public enum EnvJsonFile {
	
	BASICFILE("\\src\\test\\java\\configuration\\basic_parameters.json"),
	TESTFILE("\\src\\test\\java\\configuration\\test_parameters.json"),
	TESTDATA("\\src\\test\\java\\configuration\\test_data.json");
	
	private String desc;//��������

    /**
     * ˽�й���,��ֹ���ⲿ����
     * @param desc
     */
    private EnvJsonFile(String desc){
        this.desc=desc;
    }

    /**
     * ���巽��,��������,��������Ķ���û����
     * @return
     */
    public String getDesc(){
        return desc;
    }

    public static void main(String[] args){
        for (EnvJsonFile day:EnvJsonFile.values()) {
            System.out.println("name:"+day.name()+
                    ",desc:"+day.getDesc());
        }
    }

}