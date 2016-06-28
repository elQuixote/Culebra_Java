//--All Behaviors incorporates an interface to control each and all culebra behaviors. If multiple behaviors are active then they will form a hybrid behavior--
//--Use 'r' key to reset the simulation--

//--***This library depends on 2 external libraries (peasy, toxiclibs) which you can download below***
//--Peasy - http://mrfeinberg.com/peasycam/
//--Toxi - https://bitbucket.org/postspectacular/toxiclibs/downloads/

//This specific example requires controlP5 which you can download below.
//--controlP5 - http://www.sojamo.de/libraries/controlP5/

import java.util.List;
import culebra.objects.*;
import culebra.viz.*;
import culebra.data.*;

import toxi.geom.*;
import toxi.processing.*;
import toxi.color.*;
import controlP5.*;
import peasy.*;

//------------------UI------------------------
Culebra_UI culebraUI;
int resetAmount = 0;
int D2resetAmount = 0;
int D3resetAmount = 0;
//------------------Creeper------------------------
Creeper creep;
List<Creeper> creeperSet;
Seeker creeperTracker;
PVector loc;
PVector vel;
//----------------UI Elements-----------------
boolean creeperBaby_toggleUI, seekerBaby_toggleUI, seeker_toggleUI;

boolean showPathButton = false;
boolean showMeshButton = false;

boolean masterBehavior_C = false;
boolean masterBehavior_D = false;
boolean masterBehavior_F = false;
boolean masterBehavior_G = true;
boolean subBehavior_AA = true;
boolean masterBehavior_A, masterBehavior_B, masterBehavior_E, masterBehavior_H, masterBehavior_I, masterBehavior_J, masterBehavior_K, masterBehavior_L, subBehavior_AB;

boolean bc_masterBehavior_C = false;
boolean bc_masterBehavior_D = false;
boolean bc_masterBehavior_F = false;
boolean bc_masterBehavior_A, bc_masterBehavior_B, bc_masterBehavior_E, bc_masterBehavior_G, bc_masterBehavior_H, bc_subBehavior_AA, bc_subBehavior_AB;

boolean s_masterBehavior_C = false;
boolean s_masterBehavior_D = false;
boolean s_masterBehavior_F = false;
boolean s_subBehavior_AB = true;
boolean s_masterBehavior_A, s_masterBehavior_B, s_masterBehavior_E, s_subBehavior_AA;

boolean bs_masterBehavior_C = false;
boolean bs_masterBehavior_D = false;
boolean bs_masterBehavior_F = false;
boolean bs_masterBehavior_A, bs_masterBehavior_B, bs_masterBehavior_E, bs_subBehavior_AA, bs_subBehavior_AB;

Button btypeA, btypeAA, btypeAB, btypeB, btypeC, btypeD, btypeE, btypeF, btypeG, btypeH;
Button seeker_btypeA, seeker_btypeAA, seeker_btypeAB, seeker_btypeB, seeker_btypeC, seeker_btypeD, seeker_btypeE, seeker_btypeF;
Button f_baby_behTab_Button, baby_btypeA, baby_btypeAA, baby_btypeAB, baby_btypeB, baby_btypeC, baby_btypeD, baby_btypeE, baby_btypeF, baby_btypeG;
Button bf_seeker_behTab_Button, bseeker_btypeA, bseeker_btypeAA, bseeker_btypeAB, bseeker_btypeB, bseeker_btypeC, bseeker_btypeD, bseeker_btypeE, bseeker_btypeF;
Button btypeJ, btypeK, btypeKK, btypeKKB;

boolean behbutarray[];
Button behbutarrayType[];

boolean bc_behbutarray[];
Button bc_behbutarrayType[];

boolean seek_behbutarray[];
Button seek_behbutarrayType[];

boolean bseek_behbutarray[];
Button bseek_behbutarrayType[];
// ---------------------Behavior Flags-----------------
boolean createTrails = true;
boolean cohFlag = false;
boolean sepFlag = false;
boolean aligFlag = false;
boolean flockFlag = false;
boolean wanderFlag = false;
boolean perlinFlag = false;
boolean pathTrackerFlag = false;
boolean chaseTailFlag = false;
boolean chaseOtherTailFlag = false;
boolean seekFlag = false;
boolean drawBoundary = true;
boolean drawConn = false;
boolean repell = false;
// ----------------------Map---------------------------
boolean applyMap = false;
float cohesionThreshold, cohesionValue, separateThreshold, separateValue, alignThreshold, alignValue, viewAngle;
// -------------------------Chasers-------------------
List<PVector> totTail;
List<PVector> allTrails;
List<ArrayList> allCombinedTrails;
List<Seeker> seekerList;
boolean triggerTailSeekers = false;
boolean triggerBabies;
boolean triggerSeekerBabies;
// ---------------------Camera and Dims---------------
boolean D2 = true;
boolean D3 = false;
// ---------------------IMAGE STUFF---------------------
public PImage img;
boolean showMap = false;
// --------------------Path Stuff--------------------
Path tempPath;
ArrayList<Path> pathList;
int pathCount = 10;
boolean drawPaths = false;
boolean setPaths = false;
boolean enablePaths = false;
float scalarProjectionDist;
float pathRadius = 20.0f;
// -------------------Spawn Stuff----------------------
boolean spawnEdge = false;
boolean spawnGround = false;
// -------------------Environ Stuff--------------------
int nHeight = 1000;
int creepCount = 300;
// -------------------Children Stuff--------------------
ArrayList<PVector> childSpawners;
ArrayList childSpawnType;
// ----------------------Camera-------------------------

Cameras cams;
CameraState state;
int camToggle = 0;
// --------------------OcTree + QTree------------------
Octree octree;
QuadTree qtree;
List<Vec3D> treeVecList;
List<Vec2D> qtreeVecList;
boolean createOctree = true;
boolean createQTree = true;
boolean showOctree = false;
boolean showQTree = false;
PImage img0, img2;
// --------------------Mesh Stuff---------------------
culebra.geometry.Mesh mesh;
boolean renderMesh;
String siteMeshFile = "Baby_G2.stl";
String siteDisplayMeshFile = "Baby_G3.stl";
// -----------------------Setup-----------------------
public void setup() {
  size(1820, 980, P3D);
  smooth();
  background(0);
  //--------Button Values----------
  if (!D2) {
    behbutarray = new boolean[]{masterBehavior_A, masterBehavior_B, masterBehavior_C, masterBehavior_D, masterBehavior_E, masterBehavior_F, masterBehavior_G, masterBehavior_H, subBehavior_AA, subBehavior_AB, masterBehavior_I, masterBehavior_J, masterBehavior_K, masterBehavior_L};
    behbutarrayType = new Button[]{btypeA, btypeB, btypeC, btypeD, btypeE, btypeF, btypeG, btypeH, btypeAA, btypeAB, btypeJ, btypeK, btypeKK, btypeKKB};
  } else {
    behbutarray = new boolean[]{masterBehavior_B, masterBehavior_C, masterBehavior_D, masterBehavior_E, masterBehavior_F, masterBehavior_G, masterBehavior_H, subBehavior_AA, subBehavior_AB, masterBehavior_I, masterBehavior_J, masterBehavior_K, masterBehavior_L};
    behbutarrayType = new Button[]{btypeB, btypeC, btypeD, btypeE, btypeF, btypeG, btypeH, btypeAA, btypeAB, btypeJ, btypeK, btypeKK, btypeKKB};
  }
  bc_behbutarray = new boolean[]{bc_masterBehavior_A, bc_masterBehavior_B, bc_masterBehavior_C, bc_masterBehavior_D, bc_masterBehavior_E, bc_masterBehavior_F, bc_masterBehavior_G, bc_subBehavior_AA, bc_subBehavior_AB};
  bc_behbutarrayType = new Button[]{baby_btypeA, baby_btypeB, baby_btypeC, baby_btypeD, baby_btypeE, baby_btypeF, baby_btypeG, baby_btypeAA, baby_btypeAB};

  seek_behbutarray = new boolean[]{s_masterBehavior_A, s_masterBehavior_B, s_masterBehavior_C, s_masterBehavior_D, s_masterBehavior_E, s_masterBehavior_F, s_subBehavior_AA, s_subBehavior_AB};
  seek_behbutarrayType = new Button[]{seeker_btypeA, seeker_btypeB, seeker_btypeC, seeker_btypeD, seeker_btypeE, seeker_btypeF, seeker_btypeAA, seeker_btypeAB};

  bseek_behbutarray = new boolean[]{bs_masterBehavior_A, bs_masterBehavior_B, bs_masterBehavior_C, bs_masterBehavior_D, bs_masterBehavior_E, bs_masterBehavior_F, bs_subBehavior_AA, bs_subBehavior_AB};
  bseek_behbutarrayType = new Button[]{bseeker_btypeA, bseeker_btypeB, bseeker_btypeC, bseeker_btypeD, bseeker_btypeE, bseeker_btypeF, bseeker_btypeAA, bseeker_btypeAB};

  //---------------------UI--------------------------
  if (this.D2) {
    if (this.resetAmount == 0 && this.D2resetAmount == 0) {
      this.culebraUI = new Culebra_UI();
      this.culebraUI.run(this);
    }
  } else {
    if (this.resetAmount == 0 && this.D3resetAmount == 0) {
      this.culebraUI = new Culebra_UI();
      this.culebraUI.run(this);
    }
  }
  //------------------IMAGES------------------------
  this.img0 = loadImage("LULZ.png");
  this.img2 = loadImage("SI.png");
  this.img = loadImage("MAP3.jpg");
  //-------------------MESH-------------------------
  this.mesh = new culebra.geometry.Mesh(siteMeshFile, siteMeshFile, this);

  //-INITIALIZE OCTREES & QTREES-
  this.octree = Data.makeOctree(width, this);
  this.qtree = Data.makeQuadtree(width, this);

  //----------INITIALIZE OBJECT LISTS---------------
  this.creeperSet = new ArrayList<Creeper>();
  this.seekerList = new ArrayList<Seeker>();
  this.allCombinedTrails = new ArrayList<ArrayList>();
  this.childSpawners = new ArrayList<PVector>();
  this.childSpawnType = new ArrayList();
  totTail = new ArrayList<PVector>();

  //----------INITIALIZE OBJECT LISTS---------------
  this.cams = new Cameras(this);
  if (!this.D2) {
    if (this.camToggle < 1) {
      this.camToggle++;
      int[] lookat = new int[] { this.width / 2, this.height / 2, this.nHeight / 2 };
      this.cams.set3DCamera(1500, 100, 10000, lookat, true);
    }
  }
  //----------------CAMERAS------------------------
  if (D2) {
    if (this.camToggle > 0) {
      this.camToggle = 0;
    }
    this.cams.set2DCamera(false);
  }
  //-----------------PATHS------------------------
  if (masterBehavior_G) {
    this.setPaths = true;
    this.enablePaths = true;
    if (this.setPaths) {
      if (this.enablePaths) {
        scalarProjectionDist = 100.0f;
      }
      pathList = new ArrayList<Path>();
    }
    if (this.setPaths) {
      for (int pth = 0; pth < this.pathCount; pth++) {
        newPath();
      }
    }
  }
  //----------CREATE THE CREEPERS-----------------
  for (int i = 0; i < creepCount; i++) {
    if (this.D2) {
      if (this.spawnEdge) {
        this.loc = new PVector(0, random(height), 0);
        this.vel = new PVector(this.culebraUI.is.getValue(), 0, 0);
      } else {
        this.loc = new PVector(random(0, width), random(0, height), 0);
        this.vel = new PVector(random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), 0);
      }
      this.creep = new Creeper(loc, vel, true, this.D3, this);
    } else {
      if (this.spawnGround) {
        this.loc = new PVector(random(width), random(height), 0);
        this.vel = new PVector(random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(0, this.culebraUI.is.getValue()));
      } else if (this.spawnEdge) {
        this.loc = new PVector(random(width), random(height), 0);
        this.vel = new PVector(random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(0, this.culebraUI.is.getValue()));
      } else {
        this.loc = new PVector(random(width), random(height), random(0, nHeight));
        this.vel = new PVector(random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()), random(-this.culebraUI.is.getValue(), this.culebraUI.is.getValue()));
      }
      this.creep = new Creeper(loc, vel, true, this.D3, this);
    }
    this.creeperSet.add(this.creep);
  }
}
// -----------------------Draw-----------------------
public void draw() {
  background(0);

  this.creepCount = (int)this.culebraUI.ac.getValue();

  if (!this.D2 && this.drawBoundary) {
    drawExtents();
  }
  //-----------------------SHOW MESH-------------------------
  if (this.showMeshButton && this.D3 && masterBehavior_F) {
    this.mesh.renderMesh();
  }
  //-----------------------UPDATE BUTTON VALUES--------------
  if (!D2) {
    behbutarray = new boolean[]{masterBehavior_A, masterBehavior_B, masterBehavior_C, masterBehavior_D, masterBehavior_E, masterBehavior_F, masterBehavior_G, masterBehavior_H, subBehavior_AA, subBehavior_AB, masterBehavior_I, masterBehavior_J, masterBehavior_K, masterBehavior_L};
    behbutarrayType = new Button[]{btypeA, btypeB, btypeC, btypeD, btypeE, btypeF, btypeG, btypeH, btypeAA, btypeAB, btypeJ, btypeK, btypeKK, btypeKKB};
  } else {
    behbutarray = new boolean[]{masterBehavior_B, masterBehavior_C, masterBehavior_D, masterBehavior_E, masterBehavior_F, masterBehavior_G, masterBehavior_H, subBehavior_AA, subBehavior_AB, masterBehavior_I, masterBehavior_J, masterBehavior_K, masterBehavior_L};
    behbutarrayType = new Button[]{btypeB, btypeC, btypeD, btypeE, btypeF, btypeG, btypeH, btypeAA, btypeAB, btypeJ, btypeK, btypeKK, btypeKKB};
  }

  bc_behbutarray = new boolean[]{bc_masterBehavior_A, bc_masterBehavior_B, bc_masterBehavior_C, bc_masterBehavior_D, bc_masterBehavior_E, bc_masterBehavior_F, bc_masterBehavior_G, bc_subBehavior_AA, bc_subBehavior_AB};
  bc_behbutarrayType = new Button[]{baby_btypeA, baby_btypeB, baby_btypeC, baby_btypeD, baby_btypeE, baby_btypeF, baby_btypeG, baby_btypeAA, baby_btypeAB};

  seek_behbutarray = new boolean[]{s_masterBehavior_A, s_masterBehavior_B, s_masterBehavior_C, s_masterBehavior_D, s_masterBehavior_E, s_masterBehavior_F, s_subBehavior_AA, s_subBehavior_AB};
  seek_behbutarrayType = new Button[]{seeker_btypeA, seeker_btypeB, seeker_btypeC, seeker_btypeD, seeker_btypeE, seeker_btypeF, seeker_btypeAA, seeker_btypeAB};

  bseek_behbutarray = new boolean[]{bs_masterBehavior_A, bs_masterBehavior_B, bs_masterBehavior_C, bs_masterBehavior_D, bs_masterBehavior_E, bs_masterBehavior_F, bs_subBehavior_AA, bs_subBehavior_AB};
  bseek_behbutarrayType = new Button[]{bseeker_btypeA, bseeker_btypeB, bseeker_btypeC, bseeker_btypeD, bseeker_btypeE, bseeker_btypeF, bseeker_btypeAA, bseeker_btypeAB};
  // -----------------------PATH STUFF-----------------------
  if (masterBehavior_G) {
    this.setPaths = true;
    this.enablePaths = true;
    if (this.setPaths && this.pathList != null) {
      for (Path pths : pathList) {
        pths.setPathRadius(this.culebraUI.pr.getValue());
        if (drawPaths) {
          stroke(175, 0, 0, 50);
          strokeWeight(pths.getPathRadius() * 2);
          noFill();
          beginShape();
          for (PVector v : pths.getPathPoints()) {
            vertex(v.x, v.y, v.z);
          }
          endShape();

          // Draw thin line for center of path
          stroke(255);
          strokeWeight(1);
          noFill();
          beginShape();
          for (PVector v : pths.getPathPoints()) {
            vertex(v.x, v.y, v.z);
          }
          endShape();
        }
      }
    }
  }
  // -------------------------TREEDATA------------------------------
  if (this.D3) {  
    if (this.flockFlag && this.createOctree) {
      Data.updateCreepersTree(this.creeperSet, this.showOctree);
    }
  } else {
    if (this.flockFlag && this.createQTree) {
      Data.updateCreepersQTree(this.creeperSet, this.showQTree);
    }
  }
  // -----------------------------------------------------------------
  // -----------------------CREEPER AGENTS---------------------------
  // -----------------------------------------------------------------
  ArrayList groupCombinedData = new ArrayList();
  int i = 0;
  for (Creeper c : this.creeperSet) {
    c.setMoveAttributes(this.culebraUI.ms.getValue(), this.culebraUI.mf.getValue(), 1.5f);
    this.allTrails = new ArrayList<PVector>();
    ArrayList groupData = new ArrayList();

    if (masterBehavior_F && this.D3) {
      if (!(c instanceof BabyCreeper)) {
        c.behavior.meshCrawl(this.mesh, this.culebraUI.mc_mt.getValue(), c.getLocation(), c.getSpeed(), this.culebraUI.mc_sp.getValue(), (int)this.culebraUI.mc_maxBabies.getValue(), triggerBabies, true, this.childSpawners, this.childSpawnType);
      } else {
        c.behavior.meshCrawl(this.mesh, this.culebraUI.mc_mt.getValue(), c.getLocation(), c.getSpeed(), this.culebraUI.mc_sp.getValue(), (int)this.culebraUI.mc_maxBabies.getValue(), triggerBabies, false, this.childSpawners, this.childSpawnType);
      }
      this.childSpawners = c.behavior.getCrawlerChildStartPositions();
      this.childSpawnType = c.behavior.getCrawlerChildSpawnType();
    }

    if (this.masterBehavior_G) {
      if (c.behavior.isSeparateActive() && c.behavior.isInstanceable() && this.culebraUI.msep.getValue() != 0) {
        c.behavior.creeperSeparate(this.culebraUI.msep.getValue(), creeperSet);
      }
      c.behavior.pathFollowerBabyMaker(this.pathList, this.culebraUI.pt.getValue(), this.culebraUI.sd.getValue(), this.culebraUI.pr.getValue(), triggerBabies, (int)this.culebraUI.mc.getValue(), true, this.childSpawners, this.childSpawnType);
      this.childSpawners = c.behavior.getChildStartPositions();
      this.childSpawnType = c.behavior.getChildSpawnType();
      // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }
    if (this.chaseTailFlag) {
      totTail.addAll(c.getTrailPoints());
      c.behavior.selfTailChaser(60.0f, 1.5f, 80.0f, 0.00f, 5.00f, totTail);
    }
    if (this.chaseOtherTailFlag) {
      allTrails.addAll(c.getTrailPoints());
      groupData.add(i);
      groupData.add(allTrails);
      groupCombinedData.add(groupData);
    }
    if (this.wanderFlag || this.masterBehavior_A || this.masterBehavior_B || masterBehavior_C || this.masterBehavior_D || subBehavior_AA || subBehavior_AB) {
      if (this.D2) {
        float change = this.culebraUI.wt.getValue();
        if (subBehavior_AA) {
          c.behavior.wander2D(true, true, change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue());
        }
        if (subBehavior_AB) {
          c.behavior.superWander2D(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue(), this.culebraUI.wrt.getValue());
        }
      } else {
        float change = this.culebraUI.wt.getValue();  
        if (this.masterBehavior_A) {
          c.behavior.wander3D(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue(), this.culebraUI.wrt.getValue());
        }
        if (this.masterBehavior_B) {
          c.behavior.wander3D_Mod(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue());
        }
        if (this.masterBehavior_C) {
          c.behavior.wander3D_Mod2(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue());
        }
        if (this.masterBehavior_D) {
          c.behavior.wander3D_Mod3(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue());
        }
        if (this.subBehavior_AA) {
          c.behavior.wander3D_subA(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue(), this.culebraUI.wrt.getValue());
        }
        if (this.subBehavior_AB) {
          c.behavior.wander3D_subB(change, this.culebraUI.wr.getValue(), this.culebraUI.wd.getValue(), this.culebraUI.wrt.getValue());
        }
      }
    }
    if (this.perlinFlag || this.masterBehavior_I || this.masterBehavior_J || masterBehavior_K || this.masterBehavior_L) {
      if (this.D2) {
        if (masterBehavior_I) {
          c.behavior.perlin(this.culebraUI.pnS.getValue(), this.culebraUI.pnST.getValue(), this.culebraUI.pnM.getValue(), this.culebraUI.pnV.getValue());
        }
      } else {
        if (masterBehavior_I) {
          c.behavior.perlin(this.culebraUI.pnS.getValue(), this.culebraUI.pnST.getValue(), this.culebraUI.pnM.getValue(), this.culebraUI.pnV.getValue());
        }
        if (this.masterBehavior_J) {
          c.behavior.noiseModified_A(this.culebraUI.pnS.getValue(), this.culebraUI.pnST.getValue(), this.culebraUI.pnM.getValue(), this.culebraUI.pnV.getValue(), this.culebraUI.pnMM.getValue(), 5.0f);
        }
        if (this.masterBehavior_K) {
          c.behavior.noiseModified_B(this.culebraUI.pnS.getValue(), this.culebraUI.pnST.getValue(), this.culebraUI.pnM.getValue(), this.culebraUI.pnV.getValue(), this.culebraUI.pnMM.getValue());
        }
        if (this.masterBehavior_L) {
          c.behavior.noiseModified_C(this.culebraUI.pnS.getValue(), this.culebraUI.pnST.getValue(), this.culebraUI.pnM.getValue(), this.culebraUI.pnV.getValue(), this.culebraUI.pnMM.getValue());
        }
      }      
      //c.behavior.perlin2DMap(80f, 7.0f, 0.0f, 0.5f, img, true, true, true);
    }
    if (this.flockFlag || this.masterBehavior_E) {
      if (this.D3) {
        if (this.culebraUI.f_dc.getValue() == 1) {
          this.drawConn = true;
        } else {
          this.drawConn = false;
        }
        if (this.createOctree) {
          c.behavior.creeperflock(this.culebraUI.f_sR.getValue(), this.culebraUI.f_cv.getValue(), this.culebraUI.f_sv.getValue(), this.culebraUI.f_av.getValue(), this.culebraUI.f_va.getValue(), creeperSet, Data.getOctree(), Data.getTreeNodes(), this.drawConn);
        } else {
          c.behavior.creeperflock(this.culebraUI.f_sR.getValue(), this.culebraUI.f_cv.getValue(), this.culebraUI.f_sv.getValue(), this.culebraUI.f_av.getValue(), this.culebraUI.f_va.getValue(), creeperSet, this.drawConn);
        }
      } else {
        if (this.culebraUI.f_dc.getValue() == 1) {
          this.drawConn = true;
        } else {
          this.drawConn = false;
        }
        if (this.createQTree) {
          c.behavior.creeperflock2DTree(this.culebraUI.f_sR.getValue(), this.culebraUI.f_cv.getValue(), this.culebraUI.f_sv.getValue(), this.culebraUI.f_av.getValue(), creeperSet, Data.getQuadtree(), Data.getQTNodes(), this.drawConn);
        } else {
          c.behavior.creeperflock2D(this.culebraUI.f_sR.getValue(), this.culebraUI.f_cv.getValue(), this.culebraUI.f_sv.getValue(), this.culebraUI.f_av.getValue(), this.culebraUI.f_va.getValue(), creeperSet, this.drawConn);
        }
      }
    }
    if (this.D2 && (this.perlinFlag || this.masterBehavior_I)) {
      c.respawn(width, height);
    }
    if (this.D3 && !masterBehavior_G) {
      c.bounce(width, height, nHeight);
    } else if (this.D2 && !pathTrackerFlag) {
      c.bounce(width, height);
    }
    if (!(c instanceof BabyCreeper)) {
      c.move((int)this.culebraUI.mts.getValue(), (int)this.culebraUI.st.getValue());
    } else if (c instanceof BabyCreeper) {
      c.move((int)this.culebraUI.babyCreeper_mts.getValue(), (int)this.culebraUI.babyCreeper_st.getValue());
    }
    // --------Flag to Create Trails--------
    if (createTrails) {
      if (!(c instanceof BabyCreeper)) {  
        // --------Draw trails with color and with gradient--------
        float colorA[] = new float[] { 1f, 0f, 0f };
        float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
        c.viz.drawGradientTrails(c.getTrailPoints(), (int)this.culebraUI.st.getValue(), colorA, colorB, this.culebraUI.t.getValue(), this.culebraUI.stw.getValue());
      } else if (c instanceof BabyCreeper) {
        if (((BabyCreeper)c).getType() == "a") {
          float colorA[] = new float[] { 120.0f, 0.0f, 0.0f };
          float colorB[] = new float[] { 0.0f, 255.0f, 255.0f };
          if (this.D2) {
            c.viz.drawGradientTrailsFX(c.getTrailPoints(), (int)this.culebraUI.babyCreeper_st.getValue(), colorA, colorB, this.culebraUI.babyCreeper_t.getValue(), this.culebraUI.babyCreeper_stw.getValue());
          } else {
            c.viz.drawGradientTrails(c.getTrailPoints(), (int)this.culebraUI.babyCreeper_st.getValue(), colorA, colorB, this.culebraUI.babyCreeper_t.getValue(), this.culebraUI.babyCreeper_stw.getValue());
          }
        } else {
          float colorA[] = new float[] { 255.0f, 0.0f, 120.0f };
          float colorB[] = new float[] { 200.0f / 60.0f, 160.0f / 100.0f, 80.0f / 30.0f };
          if (this.D2) {
            c.viz.drawGradientTrailsFX(c.getTrailPoints(), (int)this.culebraUI.babyCreeper_st.getValue(), colorA, colorB, this.culebraUI.babyCreeper_t.getValue(), this.culebraUI.babyCreeper_stw.getValue());
          } else {
            c.viz.drawGradientTrails(c.getTrailPoints(), (int)this.culebraUI.babyCreeper_st.getValue(), colorA, colorB, this.culebraUI.babyCreeper_t.getValue(), this.culebraUI.babyCreeper_stw.getValue());
          }
        }
      }
    }    
    if (!(c instanceof BabyCreeper)) {  
      if (this.culebraUI.hw.getValue() != 0) {
        pushStyle();
        stroke(255);
        strokeWeight(this.culebraUI.hw.getValue());
        point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
        popStyle();
      }
    } else if (c instanceof BabyCreeper) {
      if (this.culebraUI.babyCreeper_hw.getValue() != 0) {
        pushStyle();
        stroke(255);
        strokeWeight(this.culebraUI.babyCreeper_hw.getValue());
        point(c.getLocation().x, c.getLocation().y, c.getLocation().z);
        popStyle();
      }
    }
    i++;
    image(img2, width-290, height-85);
    image(img0, 0, height-105);
    textSize(20);
    text("Framerate: " + (frameRate), 80, height - 6);
  }
  // -----------------------SEEKER SETUP DATA------------------------
  if (this.chaseOtherTailFlag) {
    allCombinedTrails.addAll(groupCombinedData);
    groupCombinedData.clear();
  }
  if (triggerTailSeekers) {
    spawnSeekers();
    triggerTailSeekers = !triggerTailSeekers;
  }
  // -----------------------------------------------------------------
  // -----------------------SEEKER AGENT TYPE------------------------
  // -----------------------------------------------------------------
  for (Seeker seek : this.seekerList) {
    seek.setMoveAttributes(this.culebraUI.seeker_ms.getValue(), this.culebraUI.seeker_mf.getValue(), 1.5f);
    if (s_masterBehavior_E) {
      seek.behavior.creeperflock(this.culebraUI.seeker_f_sR.getValue(), this.culebraUI.seeker_f_cv.getValue(), this.culebraUI.seeker_f_sv.getValue(), this.culebraUI.seeker_f_av.getValue(), this.culebraUI.seeker_f_va.getValue(), creeperSet, false);
    }
    if (this.chaseOtherTailFlag) {
      seek.behavior.trailFollowerBabyMaker(allCombinedTrails, this.culebraUI.seeker_pt.getValue(), this.culebraUI.seeker_sd.getValue(), this.culebraUI.seeker_pr.getValue(), this.triggerSeekerBabies, 2, true, this.childSpawners, this.childSpawnType);
    }
    if (this.s_masterBehavior_A || this.s_masterBehavior_B || s_masterBehavior_C || this.s_masterBehavior_D || this.s_subBehavior_AA || this.s_subBehavior_AB) {

      if (this.D2) {
        float change = this.culebraUI.seeker_wt.getValue();
        if (s_subBehavior_AA) {
          seek.behavior.wander2D(true, true, change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue());
        }
        if (s_subBehavior_AB) {
          seek.behavior.superWander2D(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue(), this.culebraUI.seeker_wrt.getValue());
        }
      } else {
        float change = this.culebraUI.seeker_wt.getValue();  
        if (this.s_masterBehavior_A) {
          seek.behavior.wander3D(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue(), this.culebraUI.seeker_wrt.getValue());
        }
        if (this.s_masterBehavior_B) {
          seek.behavior.wander3D_Mod(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue());
        }
        if (this.s_masterBehavior_C) {
          seek.behavior.wander3D_Mod2(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue());
        }
        if (this.s_masterBehavior_D) {
          seek.behavior.wander3D_Mod3(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue());
        }
        if (this.s_subBehavior_AA) {
          seek.behavior.wander3D_subA(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue(), this.culebraUI.seeker_wrt.getValue());
        }
        if (this.s_subBehavior_AB) {
          seek.behavior.wander3D_subB(change, this.culebraUI.seeker_wr.getValue(), this.culebraUI.seeker_wd.getValue(), this.culebraUI.seeker_wrt.getValue());
        }
      }
    }
    // if (this.perlinFlag) {
    // seek.behavior.perlin(this.img, applyMap, 80f, 7.0f, 0.0f, 0.5f);
    // }
    if (this.D3) {
      //c.respawn(width, height, nHeight,true,false);
      seek.bounce(width, height, nHeight);
    } else {
      seek.bounce(width, height);
    }
    seek.move((int)this.culebraUI.seeker_mts.getValue(), (int)this.culebraUI.seeker_st.getValue());
    // System.out.println(seek.getBehaviorType());
    if (createTrails) {
      // c.viz.createTrails();
      // c.viz.setTrailData(c.getTrailPoints());

      // --------Draw trails with color and with gradient--------
      // float colorA[] = new float[] { 1f, 0f, 0f };
      // float colorB[] = new float[] { 0.73f, 0.84f, 0.15f };
      // c.viz.drawTrails(c.getTrailPoints(), true, 100, colorA,
      // colorB, 255.0f, 1.0f);
      // --------------------------------------------------------
      // --------Draw trails with color--------------------------
      // float colorA[] = new float[] {255f, 0f, 0f};
      // c.viz.drawTrails(c.getTrailPoints(), true, colorA, 255, 1);
      // c.viz.drawTrails(c.getTrailPoints(), true);
      // --------------------------------------------------------
      // seek.viz.drawTrails(seek.getTrailPoints(), true);
      float colorA[] = new float[] { 255.0f, 0f, 0f };
      seek.viz.drawTrails(seek.getTrailPoints(), true, (int)this.culebraUI.seeker_st.getValue(), colorA, this.culebraUI.seeker_t.getValue(), this.culebraUI.seeker_stw.getValue(), false);
      // trailData = c.viz.getTrailData();
    }
    pushStyle();
    stroke(255);
    strokeWeight(4);
    point(seek.getLocation().x, seek.getLocation().y, seek.getLocation().z);
    popStyle();
    i++;
  }
  if (this.childSpawners.size() > 0) {
    newDude();
    this.childSpawners = new ArrayList<PVector>();
    this.childSpawnType = new ArrayList();
  }

  this.allCombinedTrails.clear();
  this.allTrails.clear();
  this.totTail.clear();

  if (this.D2) {
    surface.setSize(width, height);
  }
  if (this.showMap == true) {
    image(img, 0, 0);
    tint(255, 120);
  }
  this.culebraUI.createGui();
}

// ------------------------Create Paths----------------------------------
public void keyPressed() {
  if (key == 'r') {
    this.resetAmount ++;
    setup();
  }
  if (key == 't')
    this.createTrails = !this.createTrails;
  if (key == 'c')
    this.cohFlag = !this.cohFlag;
  if (key == 'a')
    this.aligFlag = !this.aligFlag;
  if (key == 's')
    this.sepFlag = !this.sepFlag;
  if (key == 'f')
    this.flockFlag = !this.flockFlag;
  if (key == 'w')
    this.wanderFlag = !this.wanderFlag;
  if (key == 'p')
    this.perlinFlag = !this.perlinFlag;
  if (key == 'C')
    this.setPaths = !this.setPaths;
  if (key == 'E')
    this.enablePaths = !this.enablePaths;
  if (key == 'P')
    drawPaths = !drawPaths;
  if (key == 'S')
    pathTrackerFlag = !pathTrackerFlag;
  if (key == 'T')
    chaseTailFlag = !chaseTailFlag;
  if (key == 'Q')
    chaseOtherTailFlag = !chaseOtherTailFlag;
  if (key == 'L')
    triggerTailSeekers = !triggerTailSeekers;
  if (key == 'B') {
    this.triggerBabies = !this.triggerBabies;
  }
  if  (key == 'G') {
    this.spawnGround = !this.spawnGround;
  }
  if (key == 'R') {
    this.repell = !this.repell;
  }
  if (key == 'k')
    seekFlag = !seekFlag;
  if (key == 'v')
    this.drawBoundary = !this.drawBoundary;
  if (key == '1')
    this.drawConn = !this.drawConn;
  if (key == 'o')
    this.showOctree = !this.showOctree;
  if (key == '5')
    this.showQTree = !this.showQTree;
  if (key == 'z')
    this.createOctree = !this.createOctree;
  if (key == 'e')
    this.createQTree = !this.createQTree;
  if (key == 'M')
    this.showMap = !this.showMap;
  if (key == '3') {
    D3 = true;
    D2 = false;
    this.D3resetAmount ++;
    this.D2resetAmount =0;
    setup();
  }
  if (key == '2') {
    D2 = true;
    D3 = false;
    this.D2resetAmount ++;
    this.D3resetAmount =0;
    setup();
  }
}
// ---------------------------------------SpawnSeekers------------------------------------------------
// ---------------------------------------------------------------------------------------------------
public void spawnSeekers() {
  for (int i = 0; i < this.culebraUI.seeker_ac.getValue(); i++) {
    PVector speed;
    if (this.D2) {
      if (this.spawnEdge) {
        this.loc = new PVector(0, random(height), 0);
        speed = new PVector(this.culebraUI.seeker_is.getValue(), 0, 0);
      } else {
        this.loc = new PVector(random(width), random(height), 0);
        speed = new PVector(random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), 0);
      }
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    } else if (!this.D2 && !spawnEdge) {
      if (this.spawnEdge) {
        this.loc = new PVector(random(width), random(height), 0);
        speed = new PVector(random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(0, this.culebraUI.seeker_is.getValue()));
      } else {
        this.loc = new PVector(random(width), random(height), random(0, nHeight));
        speed = new PVector(random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()));
      }
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    } else {
      this.loc = new PVector(width, random(height), random(0, nHeight));
      speed = new PVector(random(-this.culebraUI.seeker_is.getValue(), -this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()), random(-this.culebraUI.seeker_is.getValue(), this.culebraUI.seeker_is.getValue()));
      this.creeperTracker = new Seeker(this.loc, speed, true, this.D3, this);
    }
    this.seekerList.add(this.creeperTracker);
  }
}

// ---------------------------------------ChildrenCreation-----------------------------------
void newDude() {
  int babyCount = 0;
  for (PVector px : this.childSpawners) {
    PVector speed;
    if (this.spawnEdge) {
      speed = new PVector(1, 0, 0);
    } else {
      speed = new PVector(random(-1, 1), random(-1, 1), 0);
    }
    Creeper a;
    if ((int) this.childSpawnType.get(babyCount) % 2 == 0) {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "a", this.D3, this);
      this.creeperSet.add(a);
    } else {
      a = new BabyCreeper(new PVector(px.x, px.y, px.z), speed, false, "b", this.D3, this);
      this.creeperSet.add(a);
    }
    babyCount++;
  }
}
// ---------------------------------------CreateStaticPaths---------------------------------
void newPath() {
  if (this.D2) {
    this.tempPath = new Path();
    this.tempPath.addPoint(random(30, 300), random(height / 4, height), 0);
    this.tempPath.addPoint(random(101, width / 2), random(0, height), 0);
    this.tempPath.addPoint(random(width / 2, width), random(0, height), 0);
    this.tempPath.addPoint(random(width - 100, width), height / 2, 0);
  } else {
    this.tempPath = new Path();

    this.tempPath.addPoint(random(0, 300), random(0, height), random(0, this.nHeight));
    this.tempPath.addPoint(random(0, width / 2), random(0, height), random(0, this.nHeight));
    this.tempPath.addPoint(random(0, width), random(0, height), random(0, this.nHeight));
    this.tempPath.addPoint(random(0, width), 0, random(0, this.nHeight));
  }
  this.pathList.add(this.tempPath);
}
void drawExtents() {
  pushStyle();
  pushMatrix();
  strokeWeight(0.5f);
  stroke(200);
  noFill();
  translate(width / 2, height / 2, this.nHeight / 2);
  box(width, height, this.nHeight);
  popMatrix();
  popStyle();
}
//-----------UI BUTTONS------------------------
void TwoD(boolean resetFlag) {
  if (resetFlag) {
    this.D2 = true;
    this.D3 = false;
    this.D2resetAmount ++;
    this.D3resetAmount =0;
    setup();
  }
}
void ThreeD(boolean resetFlag) {
  if (resetFlag) {
    this.D2 = false;
    this.D3 = true;
    this.D3resetAmount ++;
    this.D2resetAmount =0;
    setup();
  }
}
void ShowPaths(boolean resetFlag) {
  if (resetFlag) {
    this.drawPaths = !this.drawPaths;
    this.showPathButton = !this.showPathButton;
  }
}
void ShowMesh(boolean resetFlag) {
  if (resetFlag) {
    this.showMeshButton = !this.showMeshButton;
  }
}
void MakeBabies(boolean resetFlag) {
  if (resetFlag) {
    this.creeperBaby_toggleUI = !this.creeperBaby_toggleUI;
    this.triggerBabies = !this.triggerBabies;
  }
}

void mc_MakeBabies(boolean resetFlag) {
  if (resetFlag) {
    this.creeperBaby_toggleUI = !this.creeperBaby_toggleUI;
    this.triggerBabies = !this.triggerBabies;
  }
}
void Wander3D(boolean resetFlag) {
  if (resetFlag) {
    this.wanderFlag = !this.wanderFlag;
    this.masterBehavior_A = !this.masterBehavior_A;
  }
}
void Wander_Mod (boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_B = !this.masterBehavior_B;
  }
}
void Wander_Mod2 (boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_C = !this.masterBehavior_C;
  }
}
void Wander_Mod3(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_D = !this.masterBehavior_D;
  }
}
void Flocking(boolean resetFlag) {
  if (resetFlag) {
    this.flockFlag = !this.flockFlag;
    this.masterBehavior_E = !this.masterBehavior_E;
  }
}
void MeshCrawl(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_F = !this.masterBehavior_F;
  }
}
void PathTracker(boolean resetFlag) {
  if (resetFlag) {
    this.pathTrackerFlag = !this.pathTrackerFlag;
    this.setPaths = !this.setPaths;
    this.enablePaths = !this.enablePaths;
    this.masterBehavior_G = !this.masterBehavior_G;
    this.resetAmount ++;
    setup();
  }
}
void CreeperSeeker(boolean resetFlag) {
  if (resetFlag) {
    this.seeker_toggleUI = !this.seeker_toggleUI;
    this.masterBehavior_H = !this.masterBehavior_H;
    this.chaseOtherTailFlag = !this.chaseOtherTailFlag;
  }
}
void SubA(boolean resetFlag) {
  if (resetFlag) {
    this.subBehavior_AA = !this.subBehavior_AA;
  }
}
void SubB(boolean resetFlag) {
  if (resetFlag) {
    this.subBehavior_AB = !this.subBehavior_AB;
  }
}
void Perlin(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_I = !this.masterBehavior_I;
  }
}
void Perlin3D(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_J = !this.masterBehavior_J;
  }
}
void Perlin3DB(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_K = !this.masterBehavior_K;
  }
}
void Perlin3DC(boolean resetFlag) {
  if (resetFlag) {
    this.masterBehavior_L = !this.masterBehavior_L;
  }
}
//-------------BABYCREEPERS---------------
void bc_Wander3D(boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_A = !this.bc_masterBehavior_A;
  }
}
void bc_Wander_Mod (boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_B = !this.bc_masterBehavior_B;
  }
}
void bc_Wander_Mod2 (boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_C = !this.bc_masterBehavior_C;
  }
}
void bc_Wander_Mod3(boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_D = !this.bc_masterBehavior_D;
  }
}
void bc_Flocking(boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_E = !this.bc_masterBehavior_E;
  }
}
void bc_MeshCrawl(boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_F = !this.bc_masterBehavior_F;
  }
}
void bc_PathTracker(boolean resetFlag) {
  if (resetFlag) {
    this.bc_masterBehavior_G = !this.bc_masterBehavior_G;
  }
}
void bc_SubA(boolean resetFlag) {
  if (resetFlag) {
    this.bc_subBehavior_AA = !this.bc_subBehavior_AA;
  }
}
void bc_SubB(boolean resetFlag) {
  if (resetFlag) {
    this.bc_subBehavior_AB = !this.bc_subBehavior_AB;
  }
}
//-------------SEEKERS---------------
void s_MakeBabies(boolean resetFlag) {
  if (resetFlag) {
    this.seekerBaby_toggleUI = !this.seekerBaby_toggleUI;
    this.triggerSeekerBabies = !this.triggerSeekerBabies;
  }
}
void s_LAUNCH_SEEKERS(boolean resetFlag) {
  if (resetFlag) {
    this.triggerTailSeekers = !this.triggerTailSeekers;
  }
}
void s_Wander3D(boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_A = !this.s_masterBehavior_A;
  }
}
void s_Wander_Mod (boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_B = !this.s_masterBehavior_B;
  }
}
void s_Wander_Mod2 (boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_C = !this.s_masterBehavior_C;
  }
}
void s_Wander_Mod3(boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_D = !this.s_masterBehavior_D;
  }
}
void s_Flocking(boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_E = !this.s_masterBehavior_E;
  }
}
void s_MeshCrawl(boolean resetFlag) {
  if (resetFlag) {
    this.s_masterBehavior_F = !this.s_masterBehavior_F;
  }
}
void s_SubA(boolean resetFlag) {
  if (resetFlag) {
    this.s_subBehavior_AA = !this.s_subBehavior_AA;
  }
}
void s_SubB(boolean resetFlag) {
  if (resetFlag) {
    this.s_subBehavior_AB = !this.s_subBehavior_AB;
  }
}
//-------------BABYSEEKERS---------------
void bs_Wander3D(boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_A = !this.bs_masterBehavior_A;
  }
}
void bs_Wander_Mod (boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_B = !this.bs_masterBehavior_B;
  }
}
void bs_Wander_Mod2 (boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_C = !this.bs_masterBehavior_C;
  }
}
void bs_Wander_Mod3(boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_D = !this.bs_masterBehavior_D;
  }
}
void bs_Flocking(boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_E = !this.bs_masterBehavior_E;
  }
}
void bs_MeshCrawl(boolean resetFlag) {
  if (resetFlag) {
    this.bs_masterBehavior_F = !this.bs_masterBehavior_F;
  }
}
void bs_SubA(boolean resetFlag) {
  if (resetFlag) {
    this.bs_subBehavior_AA = !this.bs_subBehavior_AA;
  }
}
void bs_SubB(boolean resetFlag) {
  if (resetFlag) {
    this.bs_subBehavior_AB = !this.bs_subBehavior_AB;
  }
}