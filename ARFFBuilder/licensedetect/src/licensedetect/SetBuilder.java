package licensedetect;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SetBuilder
{
	Map<String, String> attributes; // name type pairing
	List<String> data;
	String relation;

	public SetBuilder()
	{
		this.attributes = new LinkedHashMap<String, String>();
		; // name type pairing
		this.data = new ArrayList<String>();
		this.relation = new String();
		;
	}

	public void addAttribute(String name, String type)
	{
		if (attributes.containsKey(name))
		{
			System.out.println("Attribute already exists");
			return;
		}
		attributes.put(name, type);
	}

	public void addData(int[] newData, String classif)
	{
		StringBuilder sb = new StringBuilder();
		for (int i : newData)
		{
			sb.append(i + ",");
		}
		sb.append(classif);
		// sb.deleteCharAt(-1);
		// System.out.println(55);
		data.add(sb.toString());
	}

	public void relation(String name)
	{
		relation = "@RELATION " + name;
	}

	public void write(String fileName)
	{
		try
		{
			File file = new File(fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(relation + "\n");
			for (String key : attributes.keySet())
			{
				output.write("@ATTRIBUTE " + key + " " + attributes.get(key)
						+ "\n");
			}
			output.write("@DATA" + "\n");
			for (String item : data)
			{
				output.write(item + "\n");
			}

			output.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
