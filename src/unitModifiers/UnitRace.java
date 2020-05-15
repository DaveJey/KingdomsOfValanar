package unitModifiers;

import java.util.ArrayList;

import technology.Technology;
import utilities.ResourcePackage;
import utilities.UnitStatPackage;

public class UnitRace extends UnitModifiers{

	public UnitRace(String name, String tooltip, String description, UnitStatPackage statBonuses,
			UnitStatPackage multiplicativeStatBonuses,
			ArrayList<Technology> prerequisites, ResourcePackage buildCost, ResourcePackage upkeepCost) {
		super(name, tooltip, description, statBonuses, multiplicativeStatBonuses, prerequisites, buildCost, upkeepCost);
		// TODO Auto-generated constructor stub
	}

}
