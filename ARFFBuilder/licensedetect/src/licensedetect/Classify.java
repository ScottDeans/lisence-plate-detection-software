package licensedetect;

import java.io.File;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

public class Classify
{
	Classifier classifier;
	Instances trainData;
	MultilayerPerceptron multi;

	public Classify(Classifier type)
	{
		// this.classifier = type;
		this.multi = new MultilayerPerceptron();
	}

	public Classify(Classifier type, String options)
	{
		// this.classifier = type;
		this.multi = new MultilayerPerceptron();
		try
		{
			// classifier.setOptions(options);
			multi.setHiddenLayers(options);
		} catch (Exception e1)
		{
			System.out.println("Could not set options");
			// return;
		}
	}

	public void load(String file)
	{
		// load data
		ArffLoader loader = new ArffLoader();

		try
		{
			loader.setFile(new File(file));
		} catch (IOException e)
		{
			System.out.println("Could not read file");
			return;
		}

		try
		{
			trainData = loader.getDataSet();
		} catch (IOException e1)
		{
			return;
			// TODO Auto-generated catch block
		}
		// Set class to id to move
		trainData.setClassIndex(trainData.numAttributes() - 1);
	}

	public void train()
	{
		// Train a classifier
		try
		{
			// classifier.buildClassifier(trainData);
			multi.buildClassifier(trainData);
		} catch (Exception e)
		{
			System.out.println("Error building tree");
		}
	}

	public void view()
	{
		System.out.println(multi.toString());
	}

	public String classify(Instances instances)
	{
		int dec;
		Attribute a = instances.attribute(instances.numAttributes() - 1);

		try
		{
			dec = (int) multi.classifyInstance(instances.instance(0));
			return a.value(dec);
		} catch (Exception e)
		{
			System.out.println("Error Classifying instance");
			return Integer.toString(-1);
		}
	}

}