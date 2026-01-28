public class Module
{
	protected String moduleName;
	protected int moduleCredits;
	
	public Module(String name, int credits)
	{
		this.moduleName = name;
		this.moduleCredits = credits;
	}
	
	public String getModuleName()
	{
		return this.moduleName;
	}
	
	public int getModuleCredits()
	{
		return this.moduleCredits;
	}
	
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	
	public void setModuleCredits(int moduleCredits)
	{
		this.moduleCredits = moduleCredits;
	}
}