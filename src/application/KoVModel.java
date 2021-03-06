package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import entities.Kingdom;
import entities.Tile;
import technology.Technology;
import tileModifiers.Terrain;
import utilities.Description;
import utilities.PackageType;
import utilities.ResourcePackage;
import utilities.ResourcePair;
import utilities.ResourceTypes;

import java.util.Scanner;

public class KoVModel {

	public static void main(String[] args) {
		LinkedList<ResourcePair> dragon = new LinkedList<ResourcePair>();
		dragon.add(new ResourcePair(ResourceTypes.Metal,1000));
		dragon.add(new ResourcePair(ResourceTypes.Wood,1000));
		dragon.add(new ResourcePair(ResourceTypes.Gold,1000));
		dragon.add(new ResourcePair(ResourceTypes.Food,1000));
		dragon.add(new ResourcePair(ResourceTypes.Happiness,5));
		dragon.add(new ResourcePair(ResourceTypes.HappyCounter,5));
		ResourcePackage testResources = new ResourcePackage(PackageType.flat, dragon);
		dragon = null; //Let the GC collect this list
		String name = "Dragon's Reach";
		String tooltip = "Player controlled faction";
		String flavor = "Faction born of the chilling north, the land is broken and scarred by years of the dragon's conflict with"
				+ " the giantfolk. It is said that the last dragon's bide their time in wait for the time the giant's tyranny comes"
				+ " to a close.";
		Description kingdomDescription = new Description(name,tooltip,flavor);
		Set<Technology> techs = new HashSet<Technology>();
		Kingdom test = new Kingdom(kingdomDescription, testResources, techs);

		System.out.println(test);
		System.out.println(test.getAvailableResources());


		Terrain[] terrainTypes = loadTerrainTypes();
		for(Terrain t : terrainTypes) {
			System.out.println(t.toString() + " cost " + t.getDevelopmentCost() + " to develop and "
					+ "produces " + t.getProduction() + " per turn once developed.");
		}


		Tile[][] tiles = loadTiles(terrainTypes);
		for(int i = 0; i < tiles.length;i++) {
			for(int j = 0; j < tiles[i].length;j++) {
				System.out.print(tiles[i][j].toString());
			}
			System.out.println();
		}
	}


	private static Terrain[] loadTerrainTypes() {
		try {
			File terrainData = new File("../KingdomsOfValanar/initializationResources/TileTypes.csv");
			FileReader fh = new FileReader(terrainData);
			BufferedReader fileScanner = new BufferedReader(fh);

			String row;
			LinkedList<Terrain> tempList = new LinkedList<Terrain>();
			while((row = fileScanner.readLine())!= null) {
				String[] data = row.split(",");
				String terrainName = data[0];
				int movementCost = Integer.parseInt(data[1]);
				ResourcePackage production = new ResourcePackage();
				ResourcePackage cost = new ResourcePackage();
				production.add(ResourceTypes.Food, Double.parseDouble(data[2]));
				production.add(ResourceTypes.Metal,Double.parseDouble(data[3]));
				production.add(ResourceTypes.Wood,Double.parseDouble(data[4]));
				production.add(ResourceTypes.Gold,Double.parseDouble(data[5]));
				cost.add(ResourceTypes.Metal,Double.parseDouble(data[6]));
				cost.add(ResourceTypes.Wood,Double.parseDouble(data[7]));
				cost.add(ResourceTypes.Gold,Double.parseDouble(data[8]));
				cost.add(ResourceTypes.Population, Double.parseDouble(data[9]));
				int importID = Integer.parseInt(data[10]);
				Terrain t = new Terrain(terrainName,movementCost,cost,production,importID);
				tempList.add(t);
			}
			fileScanner.close();
			int length = tempList.size();
			Terrain[] terrain = new Terrain[length];
			for(Terrain t : tempList) {
				terrain[t.importID] = t;
			}
			return terrain;

		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private static Tile[][] loadTiles(Terrain[] terrainTypes) {
		try {		
			File tileData = new File("../KingdomsOfValanar/initializationResources/TileData.csv");
			FileReader fh = new FileReader(tileData);
			BufferedReader fileScanner = new BufferedReader(fh);

			String line;
			int maxCol = 0;
			int row = 0;
			LinkedList<Tile> tiles = new LinkedList<Tile>();
			while((line = fileScanner.readLine())!= null) {
				String[] data = line.split(",");
				if(data.length > 1) {
					for(int column = 0; column < data.length;column++) {
						String temp = data[column].substring(1, data[column].length()-1);
						String[] values = temp.split("`");
						int importID = Integer.parseInt(values[1]);
						Tile t = new Tile(terrainTypes[importID],row,column);					
						tiles.add(t);
						if(column > maxCol)
							maxCol = column;
					}
					row++;
				}
			}
			fileScanner.close();
			Tile[][] tileMap = new Tile[row][maxCol+1];
			for(Tile t : tiles) {
				tileMap[t.getRow()][t.getColumn()] = t;			
			}
			return tileMap;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}




}
