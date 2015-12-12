package licensedetect;

import java.io.File;
import java.io.IOException;

import ij.ImagePlus;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import weka.classifiers.functions.MultilayerPerceptron;

public class classifying
{
	public static void main(String[] args)
	{
		Object[] options =
		{ "ALP/nonALP", "Four/Five/Other" };
		int n = JOptionPane.showOptionDialog(null,
				"Which ARFF file are you making?", "Question Selection",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (n == 0)
		{
			setTrainData();
		} else if (n == 1)
		{
			setNumberTrainData();
		}
		/*
		 * previoulsy used to test individual pictures after training.
		 * 
		Classify w = new Classify(new MultilayerPerceptron(), "10"); // new classifier
		w.load("licenseData.arff");
		w.train();
		w.view();

		Classify number = new Classify(new MultilayerPerceptron(), "10"); // new
																			// classifier
		number.load("numberData.arff");
		number.train();
		number.view();

		Instances instances = alpInstance();
		String province = w.classify(instances);
		System.out.println("it is a " + province);
		Instances numinstances = numInstance();
		String lastDigit = number.classify(numinstances);
		System.out.println("it is a " + lastDigit);
		 */
	}

	// Creates an ALP Instance to test on
	public static Instances alpInstance()
	{
		// Build Instance
		SetBuilder setbuild = new SetBuilder();
		setbuild.relation("test");
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			int pixels[] = APLPixels(fc.getSelectedFile().getAbsolutePath());
			for (int i = 1; i <= 1638; i++)
			{
				setbuild.addAttribute(Integer.toString(i), "numeric");
			}
			setbuild.addAttribute("class", "{ALP,NONALP}");
			setbuild.addData(pixels, "?");// will not work until fixed numbers
		}

		setbuild.write("test");
		ArffLoader loader = new ArffLoader();
		Instances trainData;
		try
		{
			loader.setFile(new File("test.arff"));
		} catch (IOException e)
		{
			System.out.println("Could not read file");
		}

		try
		{
			trainData = loader.getDataSet();
			trainData.setClassIndex(trainData.numAttributes() - 1);
			return trainData;
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
		}
		// Set class to id to move

		return null;
	}

	// Creates a number Instance to test on
	public static Instances numInstance()
	{
		// Build Instance
		SetBuilder setbuild = new SetBuilder();
		setbuild.relation("testnum");
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			int pixels[] = NumberPixels(fc.getSelectedFile().getAbsolutePath());
			for (int i = 1; i <= 1440; i++)
			{
				setbuild.addAttribute(Integer.toString(i), "numeric");
			}
			setbuild.addAttribute("class", "{4,5,other}");
			setbuild.addData(pixels, "?");// will not work until fixed numbers
		}

		setbuild.write("testnum");
		ArffLoader loader = new ArffLoader();
		Instances trainData;
		try
		{
			loader.setFile(new File("testnum.arff"));
		} catch (IOException e)
		{
			System.out.println("Could not read file");
		}

		try
		{
			trainData = loader.getDataSet();
			trainData.setClassIndex(trainData.numAttributes() - 1);
			return trainData;
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
		}
		// Set class to id to move

		return null;
	}

	// create an ALP arff file
	public static void setTrainData()
	{
		SetBuilder setbuild = new SetBuilder();
		setbuild.relation("LicenseData");
		for (int i = 1; i <= 1452; i++)
		{
			setbuild.addAttribute(Integer.toString(i), "numeric");
		}
		setbuild.addAttribute("class", "{ALP,NONALP}");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showDialog(fc, "Open ALP");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			for (File f : fc.getSelectedFile().listFiles())
			{
				if (f.isFile())
				{
					int pixels[] = APLPixels(f.getAbsolutePath());
					System.out.println("length:" + pixels.length);
					setbuild.addData(pixels, "ALP");
				}
			}
		}

		returnVal = fc.showDialog(fc, "Open NONALP");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			for (File f : fc.getSelectedFile().listFiles())
			{
				if (f.isFile())
				{
					int pixels[] = APLPixels(f.getAbsolutePath());
					System.out.println("length:" + pixels.length);
					setbuild.addData(pixels, "NONALP");
				}
			}
		}

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		returnVal = fc.showDialog(fc, "Save ARFF");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			setbuild.write(fc.getSelectedFile().getAbsolutePath());
		}
	}

	// creates a number arff file
	public static void setNumberTrainData()
	{
		SetBuilder setbuild = new SetBuilder();
		setbuild.relation("numberData");
		for (int i = 1; i <= 1500; i++)
		{
			setbuild.addAttribute(Integer.toString(i), "numeric");
		}
		setbuild.addAttribute("class", "{4,5,other}");

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showDialog(fc, "Open FOUR");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			for (File f : fc.getSelectedFile().listFiles())
			{
				if (f.isFile())
				{
					int pixels[] = NumberPixels(f.getAbsolutePath());
					System.out.println("length:" + pixels.length);
					setbuild.addData(pixels, "4");
				}
			}
		}

		returnVal = fc.showDialog(fc, "Open FIVE");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			for (File f : fc.getSelectedFile().listFiles())
			{
				if (f.isFile())
				{
					int pixels[] = NumberPixels(f.getAbsolutePath());
					System.out.println("length:" + pixels.length);
					setbuild.addData(pixels, "5");
				}
			}
		}

		returnVal = fc.showDialog(fc, "Open OTHER");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			for (File f : fc.getSelectedFile().listFiles())
			{
				if (f.isFile())
				{
					int pixels[] = NumberPixels(f.getAbsolutePath());
					System.out.println("length:" + pixels.length);
					setbuild.addData(pixels, "other");
				}
			}
		}

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		returnVal = fc.showDialog(fc, "Save ARFF");
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			setbuild.write(fc.getSelectedFile().getAbsolutePath());
		}

	}

	// Collects rgb pixel data for a file, selecting two areas for ALP
	public static int[] APLPixels(String filename)
	{
		ImagePlus im = new ImagePlus(filename);
		int maxX = 41;
		final int maxI = 1452;
		int startX = 16;
		int[] pixels = new int[maxI];
		int[] temp;
		int currentI;
		int currentX = startX;
		int currentY = 4;

		for (currentI = 0; currentI < (maxI - 2); currentI++)
		{
			if (currentY == 18 && currentX == 16)
			{
				startX = 20;
				currentX = startX;
				maxX = 25;
			}
			temp = im.getPixel(currentX, currentY);
			pixels[currentI++] = temp[0];
			pixels[currentI++] = temp[1];
			pixels[currentI] = temp[2];
			currentX++;
			if (currentX > maxX)
			{
				currentY++;
				currentX = startX;
			}
		}
		return pixels;
	}

	// collects rgb pixel data for a file, selecting closer to the last number
	// on a plate
	public static int[] NumberPixels(String filename)
	{
		ImagePlus im = new ImagePlus(filename);

		final int maxX = 66;
		final int maxI = 1500;
		final int startX = 47;
		int[] pixels = new int[maxI];
		int[] temp;
		int currentI;
		int currentX = startX;
		int currentY = 9;

		for (currentI = 0; currentI < (maxI - 2); currentI++)
		{
			temp = im.getPixel(currentX, currentY);
			pixels[currentI++] = temp[0];
			pixels[currentI++] = temp[1];
			pixels[currentI] = temp[2];
			currentX++;
			if (currentX > maxX)
			{
				currentY++;
				currentX = startX;
			}
		}
		return pixels;
	}
}
