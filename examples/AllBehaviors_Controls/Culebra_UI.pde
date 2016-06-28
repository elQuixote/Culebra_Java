class Culebra_UI { //<>//

  PApplet parent;
  ControlP5 cp5;
  int abc = 100;

  int uiHit = 0;

  Slider pr, sd, pt, wr, wd, wrt, wt, mc, is, ms, mf, msep, ac, st, sct, hw, stw, t, mts;
  Slider seeker_is, seeker_ms, seeker_mf, seeker_msep;
  Slider c_sd, c_pt, c_wr, c_wd, c_wrt, c_wt, c_is, c_ms, c_mf, c_msep, c_ct, c_mvr, c_mxvr, c_mvA, c_MvA, c_hw, c_t, c_stw;
  Slider f_sR, f_av, f_sv, f_cv, f_dc, f_va;
  Slider mc_sp, mc_mt, mc_maxBabies;
  Button cbut, showPaths, showMesh;
  Toggle se, d, t10;
  List<Slider> babyCreeperSliderList = new ArrayList<Slider>();
  List<Slider> babySeekerSliderList = new ArrayList<Slider>();
  List<Slider> seekerSliderList = new ArrayList<Slider>();
  List<Button> seekerButtonList = new ArrayList<Button>();
  List<Button> creeperButtonList = new ArrayList<Button>();
  List<Button> babyCreeperMasterButtonList = new ArrayList<Button>();
  List<Button> babySeekerMasterButtonList = new ArrayList<Button>();
  List<Button> babyCreeperButtonList = new ArrayList<Button>();
  List<Button> babySeekerButtonList = new ArrayList<Button>();

  List<ArrayList<Integer>> shiftList = new ArrayList<ArrayList<Integer>>();
  List<ArrayList<Integer>> babySeeker_shiftList = new ArrayList<ArrayList<Integer>>();

  boolean created = false;

  Button control, seekerControl, spawn, spawnEdge, dim2, dim3, vizButt, t_beh_Button, tr_beh_Button, f_beh_Button, mc_beh_Button, mc_triggerBabies, f_behTab_Button, makeBabies, f_seeker_behTab_Button;
  Button seeker_spawn, seeker_spawnEdge, seeker_vizButt;
  Slider seeker_ac;

  Button seeker_f_beh_Button, seeker_t_beh_Button;
  Slider seeker_hw, seeker_stw, seeker_t, seeker_st;
  Slider seeker_wr, seeker_wd, seeker_wrt, seeker_wt, seeker_mts;
  Slider seeker_f_sR, seeker_f_av, seeker_f_sv, seeker_f_cv, seeker_f_dc, seeker_f_va;

  Button seeker_tr_beh_Button, seeker_makeBabies, seeker_LaunchSeekers;
  Slider seeker_pr, seeker_sd, seeker_pt, seeker_mc;

  Button babyCreeper_spawn, babyCreeper_spawnEdge;
  Slider babyCreeper_ac;

  Button babyCreeper_vizButt;
  Slider babyCreeper_hw, babyCreeper_stw, babyCreeper_t, babyCreeper_st;

  Button babyCreeper_t_beh_Button, babyCreeper_f_beh_Button, babyCreeper_mc_beh_Button;
  Slider babyCreeper_wr, babyCreeper_wd, babyCreeper_wrt, babyCreeper_wt;
  Slider babyCreeper_f_sR, babyCreeper_f_av, babyCreeper_f_sv, babyCreeper_f_cv, babyCreeper_f_dc;
  Slider babyCreeper_mc_sp, babyCreeper_mc_mt, babyCreeper_mts;

  Button babyCreeper_tr_beh_Button;
  Slider babyCreeper_pr, babyCreeper_sd, babyCreeper_pt, babyCreeper_f_va;

  Button babySeeker_spawn, babySeeker_vizButt, babySeeker_t_beh_Button, babySeeker_f_beh_Button, babySeeker_mc_beh_Button, babySeeker_tr_beh_Button, babySeeker_spawnEdge, babySeeker_cbut;
  Slider babySeeker_ac, babySeeker_hw, babySeeker_stw, babySeeker_t, babySeeker_st;
  Slider babySeeker_wr, babySeeker_wd, babySeeker_wrt, babySeeker_wt;
  Slider babySeeker_f_sR, babySeeker_f_av, babySeeker_f_sv, babySeeker_f_cv, babySeeker_f_dc;
  Slider babySeeker_mc_sp, babySeeker_mc_mt;
  Slider babySeeker_pr, babySeeker_sd, babySeeker_pt;
  Slider babySeeker_c_is, babySeeker_c_ms, babySeeker_c_mf, babySeeker_msep, babySeeker_f_va;

  Button perlin_beh_Button;
  Slider pnS, pnST, pnM, pnMM, pnV;

  int cummulative, cummulative2, cummulative3, cummulative4, cummulative5, cummulative6, cummulative7, cummulative8, cummulative9, cummulative10, cummulative11, cummulative12, cummulative13, cummulative14;

  public Culebra_UI () {
  }

  public void run(PApplet _parent) {
    this.parent = _parent;
    this.cp5 = new ControlP5(parent);

    if (!D2) {
      this.cp5.setAutoDraw(false);
    } else {
      this.cp5.setAutoDraw(true);
    }

    this.cp5.setColorForeground(color(0, 255, 0));
    this.cp5.setColorValueLabel(color(255, 255, 255));
    this.cp5.setBackground(color(0, 0, 0));
    this.cp5.setColorActive(color(0, 255, 0));
    this.cp5.setColorBackground(color(50, 50, 50));

    makeCreeperBehaviorElements();
    makeCreeperElements();
    makeSeekerBehaviorElements();
    makeSeekerElements();
    makeBabyCreeperBehaviorElements();
    makeBabyCreeperElements();
    makeBabySeekerBehaviorElements();
    makeBabySeekerElements();
  }
  public void makeCreeperElements() {
    //-------------------------------------------------------------------------------------------------------------
    //-----------------------------------------CREEPERS------------------------------------------------------------
    //-----------------------------------------CONTROL-------------------------------------------------------------
    int controlWidth = 10;
    int controlSpacing = 12;
    int controlHeader = 10;
    int controlHeaderGap = 10;

    control = cp5.addButton("---Controls---").plugTo(parent).setPosition(controlWidth, controlSpacing).setSize(100, 5);
    control.setColorActive(color(0, 255, 0));
    control.setColorBackground(color(255, 0, 0));

    this.is = cp5.addSlider("InitSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(controlWidth, controlHeader + controlHeaderGap).setValue(2.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.ms = cp5.addSlider("MaxSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(controlWidth, controlHeader + controlHeaderGap + controlSpacing).setValue(7.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.mf = cp5.addSlider("MaxForce").plugTo(parent).setRange(0.00, 2.00).setPosition(controlWidth, controlHeader + controlHeaderGap + (controlSpacing*2)).setValue(0.85).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.msep = cp5.addSlider("MaxSep").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(controlWidth, controlHeader + controlHeaderGap + (controlSpacing*3)).setValue(0.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;

    cummulative = controlHeader + controlHeaderGap + (controlSpacing * 3);
    //--------------------------------------ENVIRONMENT-------------------------------------------------------------
    int envWidth = 10;
    int envSpacing = 12;
    int envHeader = 10;
    int envHeaderGap = 10;

    spawn = cp5.addButton("---ENVIRO---").plugTo(parent).setPosition(envWidth, envSpacing + cummulative).setSize(100, 5);
    spawn.setColorActive(color(0, 255, 0));
    spawn.setColorBackground(color(255, 0, 0));

    this.ac = cp5.addSlider("AgentCount").plugTo(parent).setRange(0.00, 2500.00).setPosition(envWidth, envHeader + envHeaderGap + cummulative).setValue(300).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.dim2 = cp5.addButton("TwoD").plugTo(parent).setPosition(envWidth, envHeader + envHeaderGap + envSpacing + cummulative).setSize(100, 10);
    this.dim3 = cp5.addButton("ThreeD").plugTo(parent).setPosition(envWidth, envHeader + envHeaderGap + + (envSpacing*2) + cummulative).setSize(100, 10);
    this.spawnEdge = cp5.addButton("SpawnRandom").plugTo(parent).setPosition(envWidth, envHeader + envHeaderGap + (envSpacing*3) + cummulative).setSize(100, 10);

    cummulative2 = controlHeader + controlHeaderGap + (controlSpacing * 3);
    //--------------------------------------VIZ-------------------------------------------------------------
    int vizWidth = 10;
    int vizSpacing = 12;
    int vizHeader = 10;
    int vizHeaderGap = 10;

    vizButt = cp5.addButton("---VIZ---").plugTo(parent).setPosition(vizWidth, vizSpacing + cummulative + cummulative2).setSize(100, 5);
    vizButt.setColorActive(color(0, 255, 0));
    vizButt.setColorBackground(color(255, 0, 0));
    this.hw = cp5.addSlider("HeadWidth").plugTo(parent).setRange(0.00f, 8.0f).setPosition(vizWidth, vizHeader + vizHeaderGap + cummulative + cummulative2).setValue(0.0f)
      .setDecimalPrecision(2).setSize(50, 10);
    this.stw = cp5.addSlider("StrokeWidth").plugTo(parent).setRange(0.00f, 10).setPosition(vizWidth, vizHeader + vizHeaderGap + vizSpacing + cummulative + cummulative2).setValue(1.5f)
      .setDecimalPrecision(2).setSize(50, 10);
    this.t = cp5.addSlider("Transparency").plugTo(parent).setRange(0.00f, 255).setPosition(vizWidth, vizHeader + vizHeaderGap + (vizSpacing*2) + cummulative + cummulative2)
      .setValue(255.0f).setDecimalPrecision(2).setSize(50, 10);   
    this.mts =  cp5.addSlider("minTrailSteps").plugTo(parent).setRange(0.00, 20.00).setPosition(vizWidth, vizHeader + vizHeaderGap + (vizSpacing*3) + cummulative + cummulative2).setValue(0).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.st =  cp5.addSlider("maxTrail").plugTo(parent).setRange(0.00, 5000.00).setPosition(vizWidth, vizHeader + vizHeaderGap + (vizSpacing*4) + cummulative + cummulative2).setValue(500).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));

    cummulative3 = vizHeader + vizHeaderGap + (vizSpacing * 4);
    //--------------------------------------WANDER BEHAVIORS-------------------------------------------------------------
    int t_beh_Width = 10;
    int t_beh_Spacing = 12;
    int t_beh_Header = 10;
    int t_beh_HeaderGap = 10;

    t_beh_Button = cp5.addButton("---WANDER BEHAVIOR---").plugTo(parent).setPosition(t_beh_Width, t_beh_Spacing + cummulative + cummulative2 + cummulative3).setSize(100, 5); 
    t_beh_Button.setColorActive(color(0, 255, 0));
    t_beh_Button.setColorBackground(color(255, 0, 0));

    this.wr = cp5.addSlider("WanderRadius").plugTo(parent).setRange(0.00, 500.00).setPosition(t_beh_Width, t_beh_Header + t_beh_HeaderGap + cummulative + cummulative2 + cummulative3).setValue(10).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.wd = cp5.addSlider("WanderDist").plugTo(parent).setRange(0.00, 100.00).setPosition(t_beh_Width, t_beh_Header + t_beh_HeaderGap + t_beh_Spacing + cummulative + cummulative2 + cummulative3).setValue(20).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.wrt = cp5.addSlider("WanderRotTrigger").plugTo(parent).setRange(0.00, 50.00).setPosition(t_beh_Width, t_beh_Header + t_beh_HeaderGap + (t_beh_Spacing * 2) + cummulative + cummulative2 + cummulative3).setValue(6).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.wt = cp5.addSlider("WanderChange").plugTo(parent).setRange(0.0, 100.00).setPosition(t_beh_Width, t_beh_Header + t_beh_HeaderGap + (t_beh_Spacing * 3) + cummulative + cummulative2 + cummulative3).setValue(2).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;

    cummulative4 = t_beh_Header + t_beh_HeaderGap + (t_beh_Spacing * 3);
    //--------------------------------------FLOCKING BEHAVIORS-------------------------------------------------------------
    int f_beh_Width = 10;
    int f_beh_Spacing = 12;
    int f_beh_Header = 10;
    int f_beh_HeaderGap = 10;

    f_beh_Button = cp5.addButton("---FLOCKING BEHAVIOR---").plugTo(parent).setPosition(f_beh_Width, f_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4).setSize(100, 5); 
    f_beh_Button.setColorActive(color(0, 255, 0));
    f_beh_Button.setColorBackground(color(255, 0, 0));

    this.f_sR = cp5.addSlider("SearchRadius").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.f_av = cp5.addSlider("AlignValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + f_beh_Spacing  + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.f_sv = cp5.addSlider("SepValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing*2)  + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(0.09).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.f_cv = cp5.addSlider("CohVal").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing*3)  + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.f_va = cp5.addSlider("ViewAngle").plugTo(parent).plugTo(parent).setRange(0.00, 360.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing*4)  + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(60).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.f_dc = cp5.addSlider("DrawConn").plugTo(parent).plugTo(parent).setRange(0.00, 1.00).setPosition(f_beh_Width, f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing*5)  + cummulative + cummulative2 + cummulative3 + cummulative4).setValue(0).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;

    cummulative5 = f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing * 5);
    //--------------------------------------CRAWL BEHAVIORS-------------------------------------------------------------
    int mc_beh_Width = 10;
    int mc_beh_Spacing = 12;
    int mc_beh_Header = 10;
    int mc_beh_HeaderGap = 10;

    mc_beh_Button = cp5.addButton("---MESHCRAWL BEHAVIOR---").plugTo(parent).setPosition(mc_beh_Width, mc_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setSize(100, 5); 
    mc_beh_Button.setColorActive(color(0, 255, 0));
    mc_beh_Button.setColorBackground(color(255, 0, 0));

    this.mc_sp = cp5.addSlider("ScalarProject").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(mc_beh_Width, mc_beh_Header + mc_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setValue(100.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.mc_mt = cp5.addSlider("MeshThreshold").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(mc_beh_Width, mc_beh_Header + mc_beh_HeaderGap + mc_beh_Spacing  + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setValue(100.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.mc_maxBabies = cp5.addSlider("MaxChild").plugTo(parent).plugTo(parent).setRange(0.00, 4.00).setPosition(mc_beh_Width, mc_beh_Header + mc_beh_HeaderGap + (mc_beh_Spacing*2)  + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setValue(2.0).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.mc_triggerBabies = cp5.addButton("mc_MakeBabies").plugTo(parent).setPosition(mc_beh_Width, mc_beh_Header + mc_beh_HeaderGap + (mc_beh_Spacing*3)  + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setSize(100, 10);
    mc_triggerBabies.setColorActive(color(0, 255, 0));
    mc_triggerBabies.setColorBackground(color(255, 0, 0));

    this.showMesh = cp5.addButton("ShowMesh").plugTo(parent).setPosition(mc_beh_Width, mc_beh_Header + mc_beh_HeaderGap + (mc_beh_Spacing*4) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5).setSize(100, 10);
    showMesh.setColorActive(color(0, 255, 0));
    showMesh.setColorBackground(color(255, 0, 0));

    this.creeperButtonList.add(showMesh);

    cummulative6 = f_beh_Header + f_beh_HeaderGap + (f_beh_Spacing * 4);
    //--------------------------------------TRACKING BEHAVIORS-------------------------------------------------------------
    int tr_beh_Width = 10;
    int tr_beh_Spacing = 12;
    int tr_beh_Header = 10;
    int tr_beh_HeaderGap = 10;

    tr_beh_Button = cp5.addButton("---TRACKING BEHAVIOR---").plugTo(parent).setPosition(tr_beh_Width, tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setSize(100, 5); 
    tr_beh_Button.setColorActive(color(0, 255, 0));
    tr_beh_Button.setColorBackground(color(255, 0, 0));

    this.pr = cp5.addSlider("PathRad").plugTo(parent).setRange(0, 100).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setValue(12.0).setDecimalPrecision(2).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.sd = cp5.addSlider("ScalarProjectDist").plugTo(parent).plugTo(parent).setRange(0.00, 100.00).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setValue(50).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.pt = cp5.addSlider("PathTresh").plugTo(parent).setRange(0.00, 1500.00).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + (tr_beh_Spacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setValue(1000.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.mc = cp5.addSlider("MaxChildren").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + (tr_beh_Spacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setValue(2).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.makeBabies = cp5.addButton("MakeBabies").plugTo(parent).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + (tr_beh_Spacing*4) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setSize(100, 10);
    makeBabies.setColorActive(color(0, 255, 0));
    makeBabies.setColorBackground(color(255, 0, 0));

    this.showPaths = cp5.addButton("ShowPaths").plugTo(parent).setPosition(tr_beh_Width, tr_beh_Header + tr_beh_HeaderGap + (tr_beh_Spacing*5) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6).setSize(100, 10);
    showPaths.setColorActive(color(0, 255, 0));
    showPaths.setColorBackground(color(255, 0, 0));

    this.creeperButtonList.add(showPaths);

    cummulative7 = tr_beh_Header + tr_beh_HeaderGap + (tr_beh_Spacing * 5);
    //---------------------------------PERLIN NOISE BEHAVIORS-------------------------------------------------------------
    int perlin_beh_Width = 10;
    int perlin_beh_Spacing = 12;
    int perlin_beh_Header = 10;
    int perlin_beh_HeaderGap = 10;

    perlin_beh_Button = cp5.addButton("---PERLIN BEHAVIOR---").plugTo(parent).setPosition(perlin_beh_Width, perlin_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setSize(100, 5); 
    perlin_beh_Button.setColorActive(color(0, 255, 0));
    perlin_beh_Button.setColorBackground(color(255, 0, 0));

    this.pnS = cp5.addSlider("Scale").plugTo(parent).setRange(0, 2000).setPosition(perlin_beh_Width, perlin_beh_Header + perlin_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(500.0).setDecimalPrecision(2).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    this.pnST = cp5.addSlider("Strength").plugTo(parent).plugTo(parent).setRange(0.00, 500).setPosition(perlin_beh_Width, perlin_beh_Header + perlin_beh_HeaderGap + perlin_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(7.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.pnM = cp5.addSlider("Multiplier").plugTo(parent).setRange(0.00, 10.00).setPosition(perlin_beh_Width, perlin_beh_Header + perlin_beh_HeaderGap + (perlin_beh_Spacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(1.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.pnV = cp5.addSlider("Velocity").plugTo(parent).setRange(0.00, 10.00).setPosition(perlin_beh_Width, perlin_beh_Header + perlin_beh_HeaderGap + (perlin_beh_Spacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(5.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    this.pnMM = cp5.addSlider("MODMult").plugTo(parent).setRange(0.00, 100.00).setPosition(perlin_beh_Width, perlin_beh_Header + perlin_beh_HeaderGap + (perlin_beh_Spacing*4) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(10.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;

    cummulative8 = perlin_beh_Header + perlin_beh_HeaderGap + (perlin_beh_Spacing * 4);
    cummulative7 = cummulative7 + cummulative8;
  }
  public void makeCreeperBehaviorElements() {
    //----------------------------------------------------------------------------------------------------------------
    //--------------------------------------CREEPER BEHAVIOR TABS-----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    int behTab_Width = 125;
    int behTab_Spacing = 12;
    int behTab_Header = 10;
    int behTab_HeaderGap = 10;

    int size = (width-250)/10;
    int tabSize = size-10;

    color creeper_inactiveCol = color(120, 120, 120);
    color creeper_activeCol = color(0, 255, 0);

    f_behTab_Button = cp5.addButton("---PARENT BEHAVIORS---").plugTo(parent).setPosition(behTab_Width, behTab_Spacing).setSize(width-260, 5); 
    f_behTab_Button.setColorActive(color(0, 255, 0));
    f_behTab_Button.setColorBackground(color(255, 0, 0));

    btypeA = cp5.addButton("Wander3D").plugTo(parent).setPosition(behTab_Width, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeA.setColorActive(creeper_activeCol);              
    btypeA.setColorBackground(creeper_inactiveCol);

    btypeAA = cp5.addButton("SubA").plugTo(parent).setPosition(behTab_Width + size/3, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeAA.setColorActive(creeper_activeCol);
    btypeAA.setColorBackground(creeper_inactiveCol);

    btypeAB = cp5.addButton("SubB").plugTo(parent).setPosition(behTab_Width + (size/3)*2, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeAB.setColorActive(creeper_activeCol);
    btypeAB.setColorBackground(creeper_inactiveCol);

    btypeB = cp5.addButton("Wander_Mod").plugTo(parent).setPosition(behTab_Width + size, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeB.setColorActive(creeper_activeCol);
    btypeB.setColorBackground(creeper_inactiveCol);

    btypeC = cp5.addButton("Wander_Mod2").plugTo(parent).setPosition(behTab_Width + size*2, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeC.setColorActive(creeper_activeCol);
    btypeC.setColorBackground(creeper_inactiveCol);

    btypeD = cp5.addButton("Wander_Mod3").plugTo(parent).setPosition(behTab_Width+ size*3, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeD.setColorActive(creeper_activeCol);
    btypeD.setColorBackground(creeper_inactiveCol);

    btypeE = cp5.addButton("Flocking").plugTo(parent).setPosition(behTab_Width+ size*4, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeE.setColorActive(creeper_activeCol);
    btypeE.setColorBackground(creeper_inactiveCol);

    btypeF = cp5.addButton("MeshCrawl").plugTo(parent).setPosition(behTab_Width+ size*5, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeF.setColorActive(creeper_activeCol);
    btypeF.setColorBackground(creeper_inactiveCol);

    btypeG = cp5.addButton("PathTracker").plugTo(parent).setPosition(behTab_Width+ size*6, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeG.setColorActive(creeper_activeCol);
    btypeG.setColorBackground(creeper_inactiveCol);

    btypeH = cp5.addButton("CreeperSeeker").plugTo(parent).setPosition(behTab_Width+ size*7, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeH.setColorActive(creeper_activeCol);
    btypeH.setColorBackground(creeper_inactiveCol);

    btypeJ = cp5.addButton("Perlin").plugTo(parent).setPosition(behTab_Width + size*8, behTab_Header+behTab_HeaderGap).setSize(tabSize, 10);
    btypeJ.setColorActive(creeper_activeCol);              
    btypeJ.setColorBackground(creeper_inactiveCol);

    btypeK = cp5.addButton("Perlin3D").plugTo(parent).setPosition(behTab_Width + size*9 -6, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeK.setColorActive(creeper_activeCol);
    btypeK.setColorBackground(creeper_inactiveCol);

    btypeKK = cp5.addButton("Perlin3DB").plugTo(parent).setPosition(behTab_Width + size*9 + (size/3)-6, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeKK.setColorActive(creeper_activeCol);
    btypeKK.setColorBackground(creeper_inactiveCol);

    btypeKKB = cp5.addButton("Perlin3DC").plugTo(parent).setPosition(behTab_Width + size*9 + (size/3)*2-6, behTab_Header+behTab_HeaderGap).setSize(tabSize/3, 10);
    btypeKKB.setColorActive(creeper_activeCol);
    btypeKKB.setColorBackground(creeper_inactiveCol);
  }
  public void makeBabyCreeperElements() {
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------BABYCREEPER CREEPERS-----------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------BABYCREEPER CONTROL-----------------------------------------------------------
    int babyCreeper_controlWidth = 10;
    int babyCreeper_controlSpacing = 12;
    int babyCreeper_controlHeader = 10;
    int babyCreeper_controlHeaderGap = 10;

    cbut = cp5.addButton("-BabyCreepControls-").plugTo(parent).setPosition(babyCreeper_controlWidth, babyCreeper_controlSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setSize(100, 5);
    cbut.setColorActive(color(0, 255, 0));
    cbut.setColorBackground(color(255, 0, 0));
    babyCreeperButtonList.add(cbut);

    this.c_is = cp5.addSlider("bc_InitSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(babyCreeper_controlWidth, babyCreeper_controlHeader + babyCreeper_controlHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(2.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.c_is);
    this.c_ms = cp5.addSlider("bc_MaxSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(babyCreeper_controlWidth, babyCreeper_controlHeader + babyCreeper_controlHeaderGap + babyCreeper_controlSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(7.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.c_ms);
    this.c_mf = cp5.addSlider("bc_MaxForce").plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_controlWidth, babyCreeper_controlHeader + babyCreeper_controlHeaderGap + (babyCreeper_controlSpacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(0.85).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.c_mf);
    this.msep = cp5.addSlider("bc_MaxSep").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(babyCreeper_controlWidth, babyCreeper_controlHeader + babyCreeper_controlHeaderGap + (babyCreeper_controlSpacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(0.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.msep);

    cummulative8 = babyCreeper_controlHeader + babyCreeper_controlHeaderGap + (babyCreeper_controlSpacing * 3);
    //--------------------------------------BABYCREEEPER ENVIRONMENT-------------------------------------------------------------
    int babyCreeper_envWidth = 10;
    int babyCreeper_envSpacing = 12;
    int babyCreeper_envHeader = 10;
    int babyCreeper_envHeaderGap = 10;

    babyCreeper_spawn = cp5.addButton("---bc_ENVIRO---").plugTo(parent).setPosition(babyCreeper_envWidth, babyCreeper_envSpacing  + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setSize(100, 5);
    babyCreeper_spawn.setColorActive(color(0, 255, 0));
    babyCreeper_spawn.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_spawn);

    this.babyCreeper_ac = cp5.addSlider("bc_AgentCount").plugTo(parent).setRange(0.00, 2500.00).setPosition(babyCreeper_envWidth, babyCreeper_envHeader + babyCreeper_envHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setValue(300).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.babyCreeper_ac);
    this.babyCreeper_spawnEdge = cp5.addButton("bc_SpawnRandom").plugTo(parent).setPosition(babyCreeper_envWidth, babyCreeper_envHeader + babyCreeper_envHeaderGap + (babyCreeper_envSpacing) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setSize(100, 10);
    babyCreeperButtonList.add(babyCreeper_spawnEdge);

    cummulative9 = babyCreeper_envHeader + babyCreeper_envHeaderGap + (babyCreeper_envSpacing);
    //--------------------------------------BABYCREEEPER VIZ-------------------------------------------------------------
    int babyCreeper_vizWidth = 10;
    int babyCreeper_vizSpacing = 12;
    int babyCreeper_vizHeader = 10;
    int babyCreeper_vizHeaderGap = 10;

    babyCreeper_vizButt = cp5.addButton("---bc_VIZ---").plugTo(parent).setPosition(babyCreeper_vizWidth, babyCreeper_vizSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setSize(100, 5);
    babyCreeper_vizButt.setColorActive(color(0, 255, 0));
    babyCreeper_vizButt.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_vizButt);

    this.babyCreeper_hw = cp5.addSlider("bc_HeadWidth").plugTo(parent).setRange(0.00f, 8.0f).setPosition(babyCreeper_vizWidth, babyCreeper_vizHeader + babyCreeper_vizHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(0.0f)
      .setDecimalPrecision(2).setSize(50, 10);
    babyCreeperSliderList.add(this.babyCreeper_hw);
    this.babyCreeper_stw = cp5.addSlider("bc_StrokeWidth").plugTo(parent).setRange(0.00f, 10).setPosition(babyCreeper_vizWidth, babyCreeper_vizHeader + babyCreeper_vizHeaderGap + babyCreeper_vizSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(1.5f)
      .setDecimalPrecision(2).setSize(50, 10);
    babyCreeperSliderList.add(this.babyCreeper_stw);
    this.babyCreeper_t = cp5.addSlider("bc_Transparency").plugTo(parent).setRange(0.00f, 255).setPosition(babyCreeper_vizWidth, babyCreeper_vizHeader + babyCreeper_vizHeaderGap + (babyCreeper_vizSpacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9)
      .setValue(255.0f).setDecimalPrecision(2).setSize(50, 10);   
    babyCreeperSliderList.add(this.babyCreeper_t);
    this.babyCreeper_mts =  cp5.addSlider("bc_minTrailSteps").plugTo(parent).setRange(0.00, 20.00).setPosition(babyCreeper_vizWidth, babyCreeper_vizHeader + babyCreeper_vizHeaderGap + (babyCreeper_vizSpacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(0).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.babyCreeper_mts);
    this.babyCreeper_st =  cp5.addSlider("bc_maxTrail").plugTo(parent).setRange(0.00, 5000.00).setPosition(babyCreeper_vizWidth, babyCreeper_vizHeader + babyCreeper_vizHeaderGap + (babyCreeper_vizSpacing*4) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(5000).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.babyCreeper_st);

    cummulative10 = babyCreeper_vizHeader + babyCreeper_vizHeaderGap + (babyCreeper_vizSpacing * 4);
    //--------------------------------------BABYCREEEPER WANDER BEHAVIORS-------------------------------------------------------------
    int babyCreeper_t_beh_Width = 10;
    int babyCreeper_t_beh_Spacing = 12;
    int babyCreeper_t_beh_Header = 10;
    int babyCreeper_t_beh_HeaderGap = 10;

    babyCreeper_t_beh_Button = cp5.addButton("---bcWANDER BEHAVIOR---").plugTo(parent).setPosition(babyCreeper_t_beh_Width, babyCreeper_t_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setSize(100, 5); 
    babyCreeper_t_beh_Button.setColorActive(color(0, 255, 0));
    babyCreeper_t_beh_Button.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_t_beh_Button);

    this.babyCreeper_wr = cp5.addSlider("bc_WanderRadius").plugTo(parent).setRange(0.00, 500.00).setPosition(babyCreeper_t_beh_Width, babyCreeper_t_beh_Header + babyCreeper_t_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(10).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_wr);
    this.babyCreeper_wd = cp5.addSlider("bc_WanderDist").plugTo(parent).setRange(0.00, 100.00).setPosition(babyCreeper_t_beh_Width, babyCreeper_t_beh_Header + babyCreeper_t_beh_HeaderGap + babyCreeper_t_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(20).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_wd);
    this.babyCreeper_wrt = cp5.addSlider("bc_WanderRotTrigger").plugTo(parent).setRange(0.00, 100.00).setPosition(babyCreeper_t_beh_Width, babyCreeper_t_beh_Header + babyCreeper_t_beh_HeaderGap + (babyCreeper_t_beh_Spacing * 2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(6).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_wrt);
    this.babyCreeper_wt = cp5.addSlider("bc_WanderTheta").plugTo(parent).setRange(0.0, 100.00).setPosition(babyCreeper_t_beh_Width, babyCreeper_t_beh_Header + babyCreeper_t_beh_HeaderGap + (babyCreeper_t_beh_Spacing * 3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(100).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_wt);
    cummulative11 = babyCreeper_t_beh_Header + babyCreeper_t_beh_HeaderGap + (babyCreeper_t_beh_Spacing * 3);
    //--------------------------------------BABYCREEEPER FLOCKING BEHAVIORS-------------------------------------------------------------
    int babyCreeper_f_beh_Width = 10;
    int babyCreeper_f_beh_Spacing = 12;
    int babyCreeper_f_beh_Header = 10;
    int babyCreeper_f_beh_HeaderGap = 10;

    babyCreeper_f_beh_Button = cp5.addButton("---bcFLOCKING BEHAVIOR---").plugTo(parent).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setSize(100, 5); 
    babyCreeper_f_beh_Button.setColorActive(color(0, 255, 0));
    babyCreeper_f_beh_Button.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_f_beh_Button);

    this.babyCreeper_f_sR = cp5.addSlider("bc_SearchRadius").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_f_sR);
    this.babyCreeper_f_av = cp5.addSlider("bc_AlignValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + babyCreeper_f_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_f_av);
    this.babyCreeper_f_sv = cp5.addSlider("bc_SepValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing*2)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.09).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_f_sv);
    this.babyCreeper_f_cv = cp5.addSlider("bc_CohVal").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing*3)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_f_cv);
    this.babyCreeper_f_va = cp5.addSlider("bc_ViewAngle").plugTo(parent).plugTo(parent).setRange(0.00, 360.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing*4)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(60).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(babyCreeper_f_va);
    this.babyCreeper_f_dc = cp5.addSlider("bc_DrawConn").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_f_beh_Width, babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing*5)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_f_dc);
    cummulative12 = babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing * 5);
    //--------------------------------------BABYCREEEPER CRAWL BEHAVIORS-------------------------------------------------------------
    int babyCreeper_mc_beh_Width = 10;
    int babyCreeper_mc_beh_Spacing = 12;
    int babyCreeper_mc_beh_Header = 10;
    int babyCreeper_mc_beh_HeaderGap = 10;

    babyCreeper_mc_beh_Button = cp5.addButton("---bcMESHCRAWL BEHAVIOR---").plugTo(parent).setPosition(babyCreeper_mc_beh_Width, babyCreeper_mc_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setSize(100, 5); 
    babyCreeper_mc_beh_Button.setColorActive(color(0, 255, 0));
    babyCreeper_mc_beh_Button.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_mc_beh_Button);

    this.babyCreeper_mc_sp = cp5.addSlider("bc_ScalarProject").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(babyCreeper_mc_beh_Width, babyCreeper_mc_beh_Header + babyCreeper_mc_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_mc_sp);
    this.babyCreeper_mc_mt = cp5.addSlider("bc_MeshThreshold").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babyCreeper_mc_beh_Width, babyCreeper_mc_beh_Header + babyCreeper_mc_beh_HeaderGap + babyCreeper_mc_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_mc_mt);
    cummulative13 = babyCreeper_f_beh_Header + babyCreeper_f_beh_HeaderGap + (babyCreeper_f_beh_Spacing);
    //--------------------------------------BABYCREEEPER TRACKING BEHAVIORS-------------------------------------------------------------
    int babyCreeper_tr_beh_Width = 10;
    int babyCreeper_tr_beh_Spacing = 12;
    int babyCreeper_tr_beh_Header = 10;
    int babyCreeper_tr_beh_HeaderGap = 10;

    babyCreeper_tr_beh_Button = cp5.addButton("---bcTRACKING BEHAVIOR---").plugTo(parent).setPosition(babyCreeper_tr_beh_Width, babyCreeper_tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setSize(100, 5); 
    babyCreeper_tr_beh_Button.setColorActive(color(0, 255, 0));
    babyCreeper_tr_beh_Button.setColorBackground(color(255, 0, 0));

    babyCreeperButtonList.add(babyCreeper_tr_beh_Button);

    this.babyCreeper_pr = cp5.addSlider("bc_PathRad").plugTo(parent).setRange(0, 100).setPosition(babyCreeper_tr_beh_Width, babyCreeper_tr_beh_Header + babyCreeper_tr_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(28.0).setDecimalPrecision(2).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babyCreeperSliderList.add(this.babyCreeper_pr);
    this.babyCreeper_sd = cp5.addSlider("bc_ScalarProjectDist").plugTo(parent).plugTo(parent).setRange(0.00, 100.00).setPosition(babyCreeper_tr_beh_Width, babyCreeper_tr_beh_Header + babyCreeper_tr_beh_HeaderGap + babyCreeper_tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(50).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_sd);
    this.babyCreeper_pt = cp5.addSlider("bc_PathTresh").plugTo(parent).setRange(0.00, 1500.00).setPosition(babyCreeper_tr_beh_Width, babyCreeper_tr_beh_Header + babyCreeper_tr_beh_HeaderGap + (babyCreeper_tr_beh_Spacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(1000.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babyCreeperSliderList.add(this.babyCreeper_pt);
    cummulative14 = babyCreeper_tr_beh_Header + babyCreeper_tr_beh_HeaderGap + (babyCreeper_tr_beh_Spacing * 2);
  }
  public void makeBabyCreeperBehaviorElements() {
    //----------------------------------------------------------------------------------------------------------------
    //------------------------------------BABYCREEPERS BEHAVIOR BUTTONS-----------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //-------BABYCREEPERS BEHAVIORS-----
    int baby_behTab_Width = 125;
    int baby_behTab_Spacing = 12;
    int baby_behTab_Header = 10;
    int baby_behTab_HeaderGap = 10;

    int baby_size = (width-250)/7;
    int baby_tabSize = baby_size-10;

    int behTab_Spacing = 12;

    color bc_inactiveCol = color(120, 120, 120);
    color bc_activeCol = color(0, 255, 0);

    int babyCreeper_BehaviorTabSep = behTab_Spacing * 2;
    int seekerBehaviorTabSep;

    if (seeker_toggleUI) {
      seekerBehaviorTabSep = behTab_Spacing*2;
    } else {
      seekerBehaviorTabSep = behTab_Spacing - 12;
    }

    f_baby_behTab_Button = cp5.addButton("---BABYCREEP BEHAVIORS---").plugTo(parent).setPosition(baby_behTab_Width, baby_behTab_Spacing + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(width-260, 5); 
    f_baby_behTab_Button.setColorActive(color(0, 255, 0));
    f_baby_behTab_Button.setColorBackground(color(255, 0, 0));
    babyCreeperMasterButtonList.add(f_baby_behTab_Button);

    ArrayList<Integer> shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width);
    shiftValues.add(baby_behTab_Spacing + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    baby_btypeA = cp5.addButton("bc_Wander3D").plugTo(parent).setPosition(baby_behTab_Width, baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize/3, 10);
    baby_btypeA.setColorActive(bc_activeCol);              
    baby_btypeA.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeA);

    baby_btypeAA = cp5.addButton("bc_SubA").plugTo(parent).setPosition(baby_behTab_Width + baby_size/3, baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize/3, 10);
    baby_btypeAA.setColorActive(bc_activeCol);
    baby_btypeAA.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width + baby_size/3);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeAA);

    baby_btypeAB = cp5.addButton("bc_SubB").plugTo(parent).setPosition(baby_behTab_Width + (baby_size/3)*2, baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize/3, 10);
    baby_btypeAB.setColorActive(bc_activeCol);
    baby_btypeAB.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width + (baby_size/3)*2);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap + seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeAB);

    baby_btypeB = cp5.addButton("bc_Wander_Mod").plugTo(parent).setPosition(baby_behTab_Width + baby_size, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeB.setColorActive(bc_activeCol);
    baby_btypeB.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width + baby_size);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeB);

    baby_btypeC = cp5.addButton("bc_Wander_Mod2").plugTo(parent).setPosition(baby_behTab_Width + baby_size*2, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeC.setColorActive(bc_activeCol);
    baby_btypeC.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width + baby_size*2);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeC);

    baby_btypeD = cp5.addButton("bc_Wander_Mod3").plugTo(parent).setPosition(baby_behTab_Width+ baby_size*3, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeD.setColorActive(bc_activeCol);
    baby_btypeD.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width+ baby_size*3);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeD);

    baby_btypeE = cp5.addButton("bc_Flocking").plugTo(parent).setPosition(baby_behTab_Width+ baby_size*4, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeE.setColorActive(bc_activeCol);
    baby_btypeE.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width+ baby_size*4);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeE);

    baby_btypeF = cp5.addButton("bc_MeshCrawl").plugTo(parent).setPosition(baby_behTab_Width+ baby_size*5, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeF.setColorActive(bc_activeCol);
    baby_btypeF.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width+ baby_size*5);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeF);

    baby_btypeG = cp5.addButton("bc_CreeperTrack").plugTo(parent).setPosition(baby_behTab_Width+ baby_size*6, baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep).setSize(baby_tabSize, 10);
    baby_btypeG.setColorActive(bc_activeCol);
    baby_btypeG.setColorBackground(bc_inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(baby_behTab_Width+ baby_size*6);
    shiftValues.add(baby_behTab_Header+baby_behTab_HeaderGap+ seekerBehaviorTabSep + babyCreeper_BehaviorTabSep);
    shiftList.add(shiftValues);

    babyCreeperMasterButtonList.add(baby_btypeG);
  }
  public void makeSeekerElements() {
    //----------------------------------------------------------------------------------------------------------------
    //--------------------------------------------SEEKERS-------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------SEEKER CONTROL---------------------------------------------------------
    int seeker_controlWidth = width - 125;
    int seeker_controlSpacing = 12;
    int seeker_controlHeader = 10;
    int seeker_controlHeaderGap = 10;

    seekerControl = cp5.addButton("---SeekerControls---").plugTo(parent).setPosition(seeker_controlWidth, seeker_controlSpacing).setSize(100, 5);
    seekerControl.setColorActive(color(0, 255, 0));
    seekerControl.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seekerControl);

    this.seeker_is = cp5.addSlider("s_InitSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(seeker_controlWidth, seeker_controlHeader + seeker_controlHeaderGap).setValue(2.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_is);
    this.seeker_ms = cp5.addSlider("s_MaxSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(seeker_controlWidth, seeker_controlHeader + seeker_controlHeaderGap + seeker_controlSpacing).setValue(7.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_ms);
    this.seeker_mf = cp5.addSlider("s_MaxForce").plugTo(parent).setRange(0.00, 2.00).setPosition(seeker_controlWidth, seeker_controlHeader + seeker_controlHeaderGap + (seeker_controlSpacing*2)).setValue(0.85).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_mf);
    this.seeker_msep = cp5.addSlider("s_MaxSep").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(seeker_controlWidth, seeker_controlHeader + seeker_controlHeaderGap + (seeker_controlSpacing*3)).setValue(0.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_msep);
    int seeker_cummulative = seeker_controlHeader + seeker_controlHeaderGap + (seeker_controlSpacing * 3);

    //--------------------------------------SEEKER ENVIRONMENT-------------------------------------------------------------
    int seeker_envWidth = width - 125;
    int seeker_envSpacing = 12;
    int seeker_envHeader = 10;
    int seeker_envHeaderGap = 10;

    seeker_spawn = cp5.addButton("---s_ENVIRO---").plugTo(parent).setPosition(seeker_envWidth, seeker_envSpacing + seeker_cummulative).setSize(100, 5);
    seeker_spawn.setColorActive(color(0, 255, 0));
    seeker_spawn.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seeker_spawn);

    this.seeker_ac = cp5.addSlider("s_AgentCount").plugTo(parent).setRange(0.00, 500.00).setPosition(seeker_envWidth, seeker_envHeader + seeker_envHeaderGap + seeker_cummulative).setValue(50).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_ac);
    this.seeker_spawnEdge = cp5.addButton("s_SpawnRandom").plugTo(parent).setPosition(seeker_envWidth, seeker_envHeader + seeker_envHeaderGap + (seeker_envSpacing) + seeker_cummulative).setSize(100, 10);
    seekerButtonList.add(seeker_spawnEdge);
    int seeker_cummulative2 = seeker_envHeader + seeker_envHeaderGap + (seeker_envSpacing);
    //--------------------------------------SEEKER VIZ-------------------------------------------------------------
    int seeker_vizWidth = width - 125;
    int seeker_vizSpacing = 12;
    int seeker_vizHeader = 10;
    int seeker_vizHeaderGap = 10;

    this.seeker_vizButt = cp5.addButton("---s_VIZ---").plugTo(parent).setPosition(seeker_vizWidth, seeker_vizSpacing + seeker_cummulative + seeker_cummulative2).setSize(100, 5);
    this.seeker_vizButt.setColorActive(color(0, 255, 0));
    this.seeker_vizButt.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seeker_vizButt);

    this.seeker_hw = cp5.addSlider("s_HeadWidth").plugTo(parent).setRange(0.00f, 8.0f).setPosition(seeker_vizWidth, seeker_vizHeader + seeker_vizHeaderGap + seeker_cummulative + seeker_cummulative2).setValue(0.0f)
      .setDecimalPrecision(2).setSize(50, 10);
    seekerSliderList.add(seeker_hw);
    this.seeker_stw = cp5.addSlider("s_StrokeWidth").plugTo(parent).setRange(0.00f, 10).setPosition(seeker_vizWidth, seeker_vizHeader + seeker_vizHeaderGap + seeker_vizSpacing + seeker_cummulative + seeker_cummulative2).setValue(1.5f)
      .setDecimalPrecision(2).setSize(50, 10);
    seekerSliderList.add(seeker_stw);
    this.seeker_t = cp5.addSlider("s_Transparency").plugTo(parent).setRange(0.00f, 255).setPosition(seeker_vizWidth, seeker_vizHeader + seeker_vizHeaderGap + (seeker_vizSpacing*2) + seeker_cummulative + seeker_cummulative2)
      .setValue(255.0f).setDecimalPrecision(2).setSize(50, 10);   
    seekerSliderList.add(seeker_t);
    this.seeker_mts =  cp5.addSlider("s_minTrailSteps").plugTo(parent).setRange(0.00, 20.00).setPosition(seeker_vizWidth, seeker_vizHeader + seeker_vizHeaderGap + (seeker_vizSpacing*3) + seeker_cummulative + seeker_cummulative2).setValue(0).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(this.seeker_mts);
    this.seeker_st =  cp5.addSlider("s_maxTrail").plugTo(parent).setRange(0.00, 5000.00).setPosition(seeker_vizWidth, seeker_vizHeader + seeker_vizHeaderGap + (seeker_vizSpacing*4) + seeker_cummulative + seeker_cummulative2).setValue(5000).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_st);
    int seeker_cummulative3 = seeker_vizHeader + seeker_vizHeaderGap + (seeker_vizSpacing * 4);

    //--------------------------------------SEEKER WANDER BEHAVIORS-------------------------------------------------------------
    int seeker_t_beh_Width = width - 125;
    int seeker_t_beh_Spacing = 12;
    int seeker_t_beh_Header = 10;
    int seeker_t_beh_HeaderGap = 10;


    seeker_t_beh_Button = cp5.addButton("---s_WANDER BEHAVIOR---").plugTo(parent).setPosition(seeker_t_beh_Width, seeker_t_beh_Spacing + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3).setSize(100, 5); 
    seeker_t_beh_Button.setColorActive(color(0, 255, 0));
    seeker_t_beh_Button.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seeker_t_beh_Button);

    this.seeker_wr = cp5.addSlider("s_WanderRadius").plugTo(parent).setRange(0.00, 500.00).setPosition(seeker_t_beh_Width, seeker_t_beh_Header + seeker_t_beh_HeaderGap + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3).setValue(10).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_wr);
    this.seeker_wd = cp5.addSlider("s_WanderDist").plugTo(parent).setRange(0.00, 100.00).setPosition(seeker_t_beh_Width, seeker_t_beh_Header + seeker_t_beh_HeaderGap + seeker_t_beh_Spacing + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3).setValue(20).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_wd);
    this.seeker_wrt = cp5.addSlider("s_WanderRotTrigger").plugTo(parent).setRange(0.00, 100.00).setPosition(seeker_t_beh_Width, seeker_t_beh_Header + seeker_t_beh_HeaderGap + (seeker_t_beh_Spacing * 2) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3).setValue(6).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_wrt);
    this.seeker_wt = cp5.addSlider("s_WanderTheta").plugTo(parent).setRange(0.0, 100.00).setPosition(seeker_t_beh_Width, seeker_t_beh_Header + seeker_t_beh_HeaderGap + (seeker_t_beh_Spacing * 3) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3).setValue(2).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_wt);
    int seeker_cummulative4 = seeker_t_beh_Header + seeker_t_beh_HeaderGap + (seeker_t_beh_Spacing * 3);
    //--------------------------------------SEEKER FLOCKING BEHAVIORS-------------------------------------------------------------
    int seeker_f_beh_Width = width - 125;
    int seeker_f_beh_Spacing = 12;
    int seeker_f_beh_Header = 10;
    int seeker_f_beh_HeaderGap = 10;

    seeker_f_beh_Button = cp5.addButton("--s_FLOCKING BEHAVIOR--").plugTo(parent).setPosition(seeker_f_beh_Width, seeker_f_beh_Spacing + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setSize(100, 5); 
    seeker_f_beh_Button.setColorActive(color(0, 255, 0));
    seeker_f_beh_Button.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seeker_f_beh_Button);

    this.seeker_f_sR = cp5.addSlider("s_SearchRadius").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_sR);
    this.seeker_f_av = cp5.addSlider("s_AlignValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + seeker_f_beh_Spacing  + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_av);
    this.seeker_f_sv = cp5.addSlider("s_SepValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + (seeker_f_beh_Spacing*2)  + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(0.09).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_sv);
    this.seeker_f_cv = cp5.addSlider("s_CohVal").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + (seeker_f_beh_Spacing*3)  + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_cv);
    this.seeker_f_va = cp5.addSlider("s_ViewAngle").plugTo(parent).plugTo(parent).setRange(0.00, 360.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + (seeker_f_beh_Spacing*4)  + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(60).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_va);
    this.seeker_f_dc = cp5.addSlider("s_DrawConn").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(seeker_f_beh_Width, seeker_f_beh_Header + seeker_f_beh_HeaderGap + (seeker_f_beh_Spacing*5)  + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_f_dc);

    int seeker_cummulative5 = seeker_f_beh_Header + seeker_f_beh_HeaderGap + (seeker_f_beh_Spacing * 5);
    //--------------------------------------SEEKER TRACKING BEHAVIORS-------------------------------------------------------------
    int seeker_tr_beh_Width = width - 125;
    int seeker_tr_beh_Spacing = 12;
    int seeker_tr_beh_Header = 10;
    int seeker_tr_beh_HeaderGap = 10;

    seeker_tr_beh_Button = cp5.addButton("--s_TRACKING BEHAVIOR--").plugTo(parent).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Spacing + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setSize(100, 5); 
    seeker_tr_beh_Button.setColorActive(color(0, 255, 0));
    seeker_tr_beh_Button.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(seeker_tr_beh_Button);  

    this.seeker_pr = cp5.addSlider("s_PathRad").plugTo(parent).setRange(0, 100).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setValue(20.0).setDecimalPrecision(2).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    seekerSliderList.add(seeker_pr);
    this.seeker_sd = cp5.addSlider("s_ScalarProjectDist").plugTo(parent).plugTo(parent).setRange(0.00, 100.00).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + seeker_tr_beh_Spacing + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setValue(25).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_sd);
    this.seeker_pt = cp5.addSlider("s_PathTresh").plugTo(parent).setRange(0.00, 1500.00).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + (seeker_tr_beh_Spacing*2) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setValue(1000.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_pt);
    this.seeker_mc = cp5.addSlider("s_MaxChildren").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + (seeker_tr_beh_Spacing*3) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setValue(4).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    seekerSliderList.add(seeker_mc);
    this.seeker_LaunchSeekers = cp5.addButton("s_LAUNCH_SEEKERS").plugTo(parent).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + (seeker_tr_beh_Spacing*4) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setSize(100, 20);
    seekerButtonList.add(seeker_LaunchSeekers);  
    seeker_LaunchSeekers.setColorActive(color(0, 255, 0));
    seeker_LaunchSeekers.setColorBackground(color(255, 0, 0));
    this.seeker_makeBabies = cp5.addButton("s_MakeBabies").plugTo(parent).setPosition(seeker_tr_beh_Width, seeker_tr_beh_Header + seeker_tr_beh_HeaderGap + (seeker_tr_beh_Spacing*5 + 12) + seeker_cummulative + seeker_cummulative2 + seeker_cummulative3 + seeker_cummulative4 + seeker_cummulative5).setSize(100, 10);
    seekerButtonList.add(seeker_makeBabies);  
    seeker_makeBabies.setColorActive(color(0, 255, 0));
    seeker_makeBabies.setColorBackground(color(255, 0, 0));
  }
  public void makeSeekerBehaviorElements() {
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------SEEKER BEHAVIOR BUTTONS-------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //-------SEEKER BEHAVIORS-----
    int seek_behTab_Width = 125;
    int seek_behTab_Spacing = 12;
    int seek_behTab_Header = 10;
    int seek_behTab_HeaderGap = 10;

    int seek_size = (width-250)/6;
    int seek_tabSize = seek_size-10;

    color inactiveCol = color(120, 120, 120);
    color activeCol = color(0, 255, 0);

    int seekerBehaviorTabSep = seek_behTab_Spacing*2;

    f_seeker_behTab_Button = cp5.addButton("---SEEKER BEHAVIORS---").plugTo(parent).setPosition(seek_behTab_Width, seek_behTab_Spacing + seekerBehaviorTabSep).setSize(width-260, 5); 
    f_seeker_behTab_Button.setColorActive(color(0, 255, 0));
    f_seeker_behTab_Button.setColorBackground(color(255, 0, 0));

    seekerButtonList.add(f_seeker_behTab_Button);

    seeker_btypeA = cp5.addButton("s_Wander3D").plugTo(parent).setPosition(seek_behTab_Width, seek_behTab_Header+seek_behTab_HeaderGap + seekerBehaviorTabSep ).setSize(seek_tabSize/3, 10);
    seeker_btypeA.setColorActive(activeCol);              
    seeker_btypeA.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeA);

    seeker_btypeAA = cp5.addButton("s_SubA").plugTo(parent).setPosition(seek_behTab_Width + seek_size/3, seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep).setSize(seek_tabSize/3, 10);
    seeker_btypeAA.setColorActive(activeCol);
    seeker_btypeAA.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeAA);

    seeker_btypeAB = cp5.addButton("s_SubB").plugTo(parent).setPosition(seek_behTab_Width + (seek_size/3)*2, seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep).setSize(seek_tabSize/3, 10);
    seeker_btypeAB.setColorActive(activeCol);
    seeker_btypeAB.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeAB);

    seeker_btypeB = cp5.addButton("s_Wander_Mod").plugTo(parent).setPosition(seek_behTab_Width + seek_size, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep).setSize(seek_tabSize, 10);
    seeker_btypeB.setColorActive(activeCol);
    seeker_btypeB.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeB);

    seeker_btypeC = cp5.addButton("s_Wander_Mod2").plugTo(parent).setPosition(seek_behTab_Width + seek_size*2, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep).setSize(seek_tabSize, 10);
    seeker_btypeC.setColorActive(activeCol);
    seeker_btypeC.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeC);

    seeker_btypeD = cp5.addButton("s_Wander_Mod3").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*3, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep).setSize(seek_tabSize, 10);
    seeker_btypeD.setColorActive(activeCol);
    seeker_btypeD.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeD);

    seeker_btypeE = cp5.addButton("s_Flocking").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*4, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep).setSize(seek_tabSize, 10);
    seeker_btypeE.setColorActive(activeCol);
    seeker_btypeE.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeE);

    seeker_btypeF = cp5.addButton("s_CreeperTrack").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*5, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep).setSize(seek_tabSize, 10);
    seeker_btypeF.setColorActive(activeCol);
    seeker_btypeF.setColorBackground(inactiveCol);

    seekerButtonList.add(seeker_btypeF);
  }
  public void makeBabySeekerElements() {
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------BABYSEEKER CREEPERS-----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //----------------------------------------BABYSEEKER CONTROL------------------------------------------------------
    int babySeeker_controlWidth = width - 125;
    int babySeeker_controlSpacing = 12;
    int babySeeker_controlHeader = 10;
    int babySeeker_controlHeaderGap = 10;

    cummulative7 = 12;

    babySeeker_cbut = cp5.addButton("-BabySeekerControls-").plugTo(parent).setPosition(babySeeker_controlWidth, babySeeker_controlSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setSize(100, 5);
    babySeeker_cbut.setColorActive(color(0, 255, 0));
    babySeeker_cbut.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_cbut);

    this.babySeeker_c_is = cp5.addSlider("bs_InitSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(babySeeker_controlWidth, babySeeker_controlHeader + babySeeker_controlHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(2.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_c_is);
    this.babySeeker_c_ms = cp5.addSlider("bs_MaxSpeed").plugTo(parent).setRange(0.00, 10.00).setPosition(babySeeker_controlWidth, babySeeker_controlHeader + babySeeker_controlHeaderGap + babySeeker_controlSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(7.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_c_ms);
    this.babySeeker_c_mf = cp5.addSlider("bs_MaxForce").plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_controlWidth, babySeeker_controlHeader + babySeeker_controlHeaderGap + (babySeeker_controlSpacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(0.85).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_c_mf);
    this.babySeeker_msep = cp5.addSlider("bs_MaxSep").plugTo(parent).plugTo(parent).setRange(0.00, 20.00).setPosition(babySeeker_controlWidth, babySeeker_controlHeader + babySeeker_controlHeaderGap + (babySeeker_controlSpacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7).setValue(0.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_msep);

    cummulative8 = babySeeker_controlHeader + babySeeker_controlHeaderGap + (babySeeker_controlSpacing * 3);
    //--------------------------------------BABYSEEKER ENVIRONMENT-------------------------------------------------------------
    int babySeeker_envWidth = width - 125;
    int babySeeker_envSpacing = 12;
    int babySeeker_envHeader = 10;
    int babySeeker_envHeaderGap = 10;

    babySeeker_spawn = cp5.addButton("---bs_ENVIRO---").plugTo(parent).setPosition(babySeeker_envWidth, babySeeker_envSpacing  + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setSize(100, 5);
    babySeeker_spawn.setColorActive(color(0, 255, 0));
    babySeeker_spawn.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_spawn);

    this.babySeeker_ac = cp5.addSlider("bs_AgentCount").plugTo(parent).setRange(0.00, 2500.00).setPosition(babySeeker_envWidth, babySeeker_envHeader + babySeeker_envHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setValue(300).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_ac);
    this.babySeeker_spawnEdge = cp5.addButton("bs_SpawnRandom").plugTo(parent).setPosition(babySeeker_envWidth, babySeeker_envHeader + babySeeker_envHeaderGap + (babySeeker_envSpacing) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8).setSize(100, 10);
    babySeekerButtonList.add(babySeeker_spawnEdge);
    cummulative9 = babySeeker_envHeader + babySeeker_envHeaderGap + (babySeeker_envSpacing);
    //--------------------------------------BABYSEEKER VIZ-------------------------------------------------------------
    int babySeeker_vizWidth = width - 125;
    int babySeeker_vizSpacing = 12;
    int babySeeker_vizHeader = 10;
    int babySeeker_vizHeaderGap = 10;

    babySeeker_vizButt = cp5.addButton("---bs_VIZ---").plugTo(parent).setPosition(babySeeker_vizWidth, babySeeker_vizSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setSize(100, 5);
    babySeeker_vizButt.setColorActive(color(0, 255, 0));
    babySeeker_vizButt.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_vizButt);

    this.babySeeker_hw = cp5.addSlider("bs_HeadWidth").plugTo(parent).setRange(0.00f, 8.0f).setPosition(babySeeker_vizWidth, babySeeker_vizHeader + babySeeker_vizHeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(0.0f)
      .setDecimalPrecision(2).setSize(50, 10);
    babySeekerSliderList.add(this.babySeeker_hw);
    this.babySeeker_stw = cp5.addSlider("bs_StrokeWidth").plugTo(parent).setRange(0.00f, 10).setPosition(babySeeker_vizWidth, babySeeker_vizHeader + babySeeker_vizHeaderGap + babySeeker_vizSpacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(1.5f)
      .setDecimalPrecision(2).setSize(50, 10);
    babySeekerSliderList.add(this.babySeeker_stw);
    this.babySeeker_t = cp5.addSlider("bs_Transparency").plugTo(parent).setRange(0.00f, 255).setPosition(babySeeker_vizWidth, babySeeker_vizHeader + babySeeker_vizHeaderGap + (babySeeker_vizSpacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9)
      .setValue(255.0f).setDecimalPrecision(2).setSize(50, 10);   
    babySeekerSliderList.add(this.babySeeker_t);
    this.babySeeker_st =  cp5.addSlider("bs_maxTrail").plugTo(parent).setRange(0.00, 5000.00).setPosition(babySeeker_vizWidth, babySeeker_vizHeader + babySeeker_vizHeaderGap + (babySeeker_vizSpacing*3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9).setValue(5000).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_st);

    cummulative10 = babySeeker_vizHeader + babySeeker_vizHeaderGap + (babySeeker_vizSpacing * 3);
    //--------------------------------------BABYSEEKER WANDER BEHAVIORS-------------------------------------------------------------
    int babySeeker_t_beh_Width = width - 125;
    int babySeeker_t_beh_Spacing = 12;
    int babySeeker_t_beh_Header = 10;
    int babySeeker_t_beh_HeaderGap = 10;

    babySeeker_t_beh_Button = cp5.addButton("---bsWANDER BEHAVIOR---").plugTo(parent).setPosition(babySeeker_t_beh_Width, babySeeker_t_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setSize(100, 5); 
    babySeeker_t_beh_Button.setColorActive(color(0, 255, 0));
    babySeeker_t_beh_Button.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_t_beh_Button);

    this.babySeeker_wr = cp5.addSlider("bs_WanderRadius").plugTo(parent).setRange(0.00, 500.00).setPosition(babySeeker_t_beh_Width, babySeeker_t_beh_Header + babySeeker_t_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(10).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_wr);
    this.babySeeker_wd = cp5.addSlider("bs_WanderDist").plugTo(parent).setRange(0.00, 100.00).setPosition(babySeeker_t_beh_Width, babySeeker_t_beh_Header + babySeeker_t_beh_HeaderGap + babySeeker_t_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(20).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_wd);
    this.babySeeker_wrt = cp5.addSlider("bs_WanderRotTrigger").plugTo(parent).setRange(0.00, 100.00).setPosition(babySeeker_t_beh_Width, babySeeker_t_beh_Header + babySeeker_t_beh_HeaderGap + (babySeeker_t_beh_Spacing * 2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(6).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_wrt);
    this.babySeeker_wt = cp5.addSlider("bs_WanderTheta").plugTo(parent).setRange(0.0, 100.00).setPosition(babySeeker_t_beh_Width, babySeeker_t_beh_Header + babySeeker_t_beh_HeaderGap + (babySeeker_t_beh_Spacing * 3) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10).setValue(100).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_wt);
    cummulative11 = babySeeker_t_beh_Header + babySeeker_t_beh_HeaderGap + (babySeeker_t_beh_Spacing * 3);
    //--------------------------------------BABYSEEKER FLOCKING BEHAVIORS-------------------------------------------------------------
    int babySeeker_f_beh_Width = width - 125;
    int babySeeker_f_beh_Spacing = 12;
    int babySeeker_f_beh_Header = 10;
    int babySeeker_f_beh_HeaderGap = 10;

    babySeeker_f_beh_Button = cp5.addButton("---bsFLOCKING BEHAVIOR---").plugTo(parent).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setSize(100, 5); 
    babySeeker_f_beh_Button.setColorActive(color(0, 255, 0));
    babySeeker_f_beh_Button.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_f_beh_Button);

    this.babySeeker_f_sR = cp5.addSlider("bs_SearchRadius").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_f_sR);
    this.babySeeker_f_av = cp5.addSlider("bs_AlignValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + babySeeker_f_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_f_av);
    this.babySeeker_f_sv = cp5.addSlider("bs_SepValue").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing*2)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.09).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_f_sv);
    this.babySeeker_f_cv = cp5.addSlider("bs_CohVal").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing*3)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_f_cv);
    this.babySeeker_f_va = cp5.addSlider("bs_ViewAngle").plugTo(parent).plugTo(parent).setRange(0.00, 360.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing*4)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(60).setDecimalPrecision(0).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(babySeeker_f_va);
    this.babySeeker_f_dc = cp5.addSlider("bs_DrawConn").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_f_beh_Width, babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing*5)+ cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11).setValue(0.24).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_f_dc);
    cummulative12 = babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing * 5);
    //--------------------------------------BABYSEEKER CRAWL BEHAVIORS-------------------------------------------------------------
    int babySeeker_mc_beh_Width = width - 125;
    int babySeeker_mc_beh_Spacing = 12;
    int babySeeker_mc_beh_Header = 10;
    int babySeeker_mc_beh_HeaderGap = 10;

    babySeeker_mc_beh_Button = cp5.addButton("---bsMESHCRAWL BEHAVIOR---").plugTo(parent).setPosition(babySeeker_mc_beh_Width, babySeeker_mc_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setSize(100, 5); 
    babySeeker_mc_beh_Button.setColorActive(color(0, 255, 0));
    babySeeker_mc_beh_Button.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_mc_beh_Button);

    this.babySeeker_mc_sp = cp5.addSlider("bs_ScalarProject").plugTo(parent).plugTo(parent).setRange(0.00, 300.00).setPosition(babySeeker_mc_beh_Width, babySeeker_mc_beh_Header + babySeeker_mc_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setValue(30).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_mc_sp);
    this.babySeeker_mc_mt = cp5.addSlider("bs_MeshThreshold").plugTo(parent).plugTo(parent).setRange(0.00, 2.00).setPosition(babySeeker_mc_beh_Width, babySeeker_mc_beh_Header + babySeeker_mc_beh_HeaderGap + babySeeker_mc_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12).setValue(0.025).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_mc_mt);
    cummulative13 = babySeeker_f_beh_Header + babySeeker_f_beh_HeaderGap + (babySeeker_f_beh_Spacing);
    //--------------------------------------BABYSEEKER TRACKING BEHAVIORS-------------------------------------------------------------
    int babySeeker_tr_beh_Width = width - 125;
    int babySeeker_tr_beh_Spacing = 12;
    int babySeeker_tr_beh_Header = 10;
    int babySeeker_tr_beh_HeaderGap = 10;

    babySeeker_tr_beh_Button = cp5.addButton("---bsTRACKING BEHAVIOR---").plugTo(parent).setPosition(babySeeker_tr_beh_Width, babySeeker_tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setSize(100, 5); 
    babySeeker_tr_beh_Button.setColorActive(color(0, 255, 0));
    babySeeker_tr_beh_Button.setColorBackground(color(255, 0, 0));

    babySeekerButtonList.add(babySeeker_tr_beh_Button);

    this.babySeeker_pr = cp5.addSlider("bs_PathRad").plugTo(parent).setRange(0, 100).setPosition(babySeeker_tr_beh_Width, babySeeker_tr_beh_Header + babySeeker_tr_beh_HeaderGap + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(28.0).setDecimalPrecision(2).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40));
    babySeekerSliderList.add(this.babySeeker_pr);
    this.babySeeker_sd = cp5.addSlider("bs_ScalarProjectDist").plugTo(parent).plugTo(parent).setRange(0.00, 100.00).setPosition(babySeeker_tr_beh_Width, babySeeker_tr_beh_Header + babySeeker_tr_beh_HeaderGap + babySeeker_tr_beh_Spacing + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(50).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_sd);
    this.babySeeker_pt = cp5.addSlider("bs_PathTresh").plugTo(parent).setRange(0.00, 1500.00).setPosition(babySeeker_tr_beh_Width, babySeeker_tr_beh_Header + babySeeker_tr_beh_HeaderGap + (babySeeker_tr_beh_Spacing*2) + cummulative + cummulative2 + cummulative3 + cummulative4 + cummulative5 + cummulative6 + cummulative7 + cummulative8 + cummulative9 + cummulative10 + cummulative11 + cummulative12 + cummulative13).setValue(1000.0).setDecimalPrecision(3).setSize(50, 10).setHandleSize(10).setColorForeground(color(255, 40)).setColorBackground(color(255, 40)) ;
    babySeekerSliderList.add(this.babySeeker_pt);
    cummulative14 = babySeeker_tr_beh_Header + babySeeker_tr_beh_HeaderGap + (babySeeker_tr_beh_Spacing * 2);
  }
  public void makeBabySeekerBehaviorElements() {
    //----------------------------------------------------------------------------------------------------------------
    //-------------------------------------BABYSEEKER BEHAVIOR BUTTONS------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------
    //-------SEEKER BEHAVIORS-----
    int seek_behTab_Width = 125;
    int seek_behTab_Spacing = 12;
    int seek_behTab_Header = 10;
    int seek_behTab_HeaderGap = 10;

    int seek_size = (width-250)/6;
    int seek_tabSize = seek_size-10;

    color inactiveCol = color(120, 120, 120);
    color activeCol = color(0, 255, 0);

    int babySeeker_BehaviorTabSep;
    int seekerBehaviorTabSep;

    if (creeperBaby_toggleUI) {
      babySeeker_BehaviorTabSep = seek_behTab_Spacing * 4;
      seekerBehaviorTabSep = seek_behTab_Spacing*2;
    } else {
      babySeeker_BehaviorTabSep = seek_behTab_Spacing + 12;
      seekerBehaviorTabSep = seek_behTab_Spacing + 12;
    }

    bf_seeker_behTab_Button = cp5.addButton("---BABY SEEKER BEHAVIORS---").plugTo(parent).setPosition(seek_behTab_Width, seek_behTab_Spacing + seekerBehaviorTabSep + babySeeker_BehaviorTabSep).setSize(width-260, 5); 
    bf_seeker_behTab_Button.setColorActive(color(0, 255, 0));
    bf_seeker_behTab_Button.setColorBackground(color(255, 0, 0));

    ArrayList<Integer> shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width);
    shiftValues.add( seek_behTab_Spacing + seekerBehaviorTabSep + babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bf_seeker_behTab_Button);

    bseeker_btypeA = cp5.addButton("bs_Wander3D").plugTo(parent).setPosition(seek_behTab_Width, seek_behTab_Header+seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep ).setSize(seek_tabSize/3, 10);
    bseeker_btypeA.setColorActive(activeCol);              
    bseeker_btypeA.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width);
    shiftValues.add(seek_behTab_Header+seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeA);

    bseeker_btypeAA = cp5.addButton("bs_SubA").plugTo(parent).setPosition(seek_behTab_Width + seek_size/3, seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize/3, 10);
    bseeker_btypeAA.setColorActive(activeCol);
    bseeker_btypeAA.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size/3);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeAA);

    bseeker_btypeAB = cp5.addButton("bs_SubB").plugTo(parent).setPosition(seek_behTab_Width + (seek_size/3)*2, seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize/3, 10);
    bseeker_btypeAB.setColorActive(activeCol);
    bseeker_btypeAB.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + (seek_size/3)*2);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap + seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeAB);

    bseeker_btypeB = cp5.addButton("bs_Wander_Mod").plugTo(parent).setPosition(seek_behTab_Width + seek_size, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize, 10);
    bseeker_btypeB.setColorActive(activeCol);
    bseeker_btypeB.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeB);

    bseeker_btypeC = cp5.addButton("bs_Wander_Mod2").plugTo(parent).setPosition(seek_behTab_Width + seek_size*2, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize, 10);
    bseeker_btypeC.setColorActive(activeCol);
    bseeker_btypeC.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size*2);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeC);

    bseeker_btypeD = cp5.addButton("bs_Wander_Mod3").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*3, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize, 10);
    bseeker_btypeD.setColorActive(activeCol);
    bseeker_btypeD.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size*3);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeD);

    bseeker_btypeE = cp5.addButton("bs_Flocking").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*4, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize, 10);
    bseeker_btypeE.setColorActive(activeCol);
    bseeker_btypeE.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size*4);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeE);

    bseeker_btypeF = cp5.addButton("bs_CreeperTrack").plugTo(parent).setPosition(seek_behTab_Width+ seek_size*5, seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep).setSize(seek_tabSize, 10);
    bseeker_btypeF.setColorActive(activeCol);
    bseeker_btypeF.setColorBackground(inactiveCol);

    shiftValues = new ArrayList<Integer>();
    shiftValues.add(seek_behTab_Width + seek_size*5);
    shiftValues.add(seek_behTab_Header+ seek_behTab_HeaderGap+ seekerBehaviorTabSep+ babySeeker_BehaviorTabSep);
    babySeeker_shiftList.add(shiftValues);

    babySeekerMasterButtonList.add(bseeker_btypeF);
  }
  public void draw() {
    background(0);
  }
  public void createGui() {
    updateUI();
    guiInit();
  }
  public void adjustBabyCreeperBehaviorElements() {
    if (seeker_toggleUI) {
      int shiftCount = 0;
      for (Button b : babyCreeperMasterButtonList) {
        float[] pos = b.getPosition();
        b.setPosition(pos[0], shiftList.get(shiftCount).get(1) + 24);
        shiftCount ++;
      }
    } else {
      int shiftCount = 0;
      for (Button b : babyCreeperMasterButtonList) {
        b.setPosition(shiftList.get(shiftCount).get(0), shiftList.get(shiftCount).get(1));
        shiftCount ++;
      }
    }
  }
  public void adjustBabySeekerBehaviorElements() {
    if (creeperBaby_toggleUI) {
      int shiftCount = 0;
      for (Button b : babySeekerMasterButtonList) {
        float[] pos = b.getPosition();
        b.setPosition(pos[0], babySeeker_shiftList.get(shiftCount).get(1) + 24);
        shiftCount ++;
      }
    } else {
      int shiftCount = 0;
      for (Button b : babySeekerMasterButtonList) {
        b.setPosition(babySeeker_shiftList.get(shiftCount).get(0), babySeeker_shiftList.get(shiftCount).get(1));
        shiftCount ++;
      }
    }
  }
  public void updateUI() {
    if (!D2) {
      if (cp5.isMouseOver()) {
        cams.setActive(false);
      } else {
        cams.setActive(true);
      }
    }

    if (seeker_toggleUI) {
      adjustBabyCreeperBehaviorElements();
    } else {
      adjustBabyCreeperBehaviorElements();
    }

    if (creeperBaby_toggleUI) {
      adjustBabySeekerBehaviorElements();
    } else {
      adjustBabySeekerBehaviorElements();
    }

    if (showPathButton) {
      showPaths.setColorBackground(color(120, 255, 0));
    } else {
      showPaths.setColorBackground(color(255, 0, 0));
    }
    if (showMeshButton) {
      showMesh.setColorBackground(color(120, 255, 0));
    } else {
      showMesh.setColorBackground(color(255, 0, 0));
    }
    if (creeperBaby_toggleUI) {
      this.makeBabies.setColorBackground(color(120, 255, 0));
    } else {
      this.makeBabies.setColorBackground(color(255, 0, 0));
    }
    //-----------Change the color of the pressed behavior to inform  the user its active for creepers---------
    int butcount = 0;
    for (boolean b : behbutarray) {
      if (b) {
        behbutarrayType[butcount].setColorBackground(color(120, 255, 0));
      } else {
        behbutarrayType[butcount].setColorBackground(color(255, 0, 0));
      }
      butcount++;
    }
    //-----------Change the color of the pressed behavior to inform  the user its active for baby creepers---------
    int bc_butcount = 0;
    for (boolean b : bc_behbutarray) {
      if (b) {
        bc_behbutarrayType[bc_butcount].setColorBackground(color(120, 255, 0));
      } else {
        bc_behbutarrayType[bc_butcount].setColorBackground(color(255, 0, 0));
      }
      bc_butcount++;
    }
    //-----------Change the color of the pressed behavior to inform  the user its active for seekers---------
    int s_butcount = 0;
    for (boolean b : seek_behbutarray) {
      if (b) {
        seek_behbutarrayType[s_butcount].setColorBackground(color(120, 255, 0));
      } else {
        seek_behbutarrayType[s_butcount].setColorBackground(color(255, 0, 0));
      }
      s_butcount++;
    }
    //-----------Change the color of the pressed behavior to inform  the user its active for baby seekers---------
    int bs_butcount = 0;
    for (boolean b : bseek_behbutarray) {
      if (b) {
        bseek_behbutarrayType[bs_butcount].setColorBackground(color(120, 255, 0));
      } else {
        bseek_behbutarrayType[bs_butcount].setColorBackground(color(255, 0, 0));
      }
      bs_butcount++;
    }

    if (!D2) {
      btypeA.setVisible(true);
      btypeB.setVisible(true);
      btypeC.setVisible(true);
      btypeD.setVisible(true);

      btypeK.setVisible(true);
      btypeKK.setVisible(true);
      btypeKKB.setVisible(true);

      baby_btypeA.setVisible(true);
      baby_btypeB.setVisible(true);
      baby_btypeC.setVisible(true);
      baby_btypeD.setVisible(true);

      seeker_btypeA.setVisible(true);
      seeker_btypeB.setVisible(true);
      seeker_btypeC.setVisible(true);
      seeker_btypeD.setVisible(true);

      bseeker_btypeA.setVisible(true);
      bseeker_btypeB.setVisible(true);
      bseeker_btypeC.setVisible(true);
      bseeker_btypeD.setVisible(true);
      
      btypeF.setVisible(true);
    } 

    //-----------Use Creeper MakeBabies Button to show and hide the baby creeper controls---------
    if (creeperBaby_toggleUI) {
      this.cbut.setVisible(true);
      this.cbut.setLabelVisible(true);
      for (Slider s : babyCreeperSliderList) {
        s.setVisible(true);
        s.setLabelVisible(true);
      }
      for (Button b : babyCreeperButtonList) {
        b.setVisible(true);
        b.setLabelVisible(true);
      }
      for (Button b : babyCreeperMasterButtonList) {
        b.setVisible(true);
        b.setLabelVisible(true);
      }
    } else {
      this.cbut.setVisible(false);
      this.cbut.setLabelVisible(false);
      for (Slider s : babyCreeperSliderList) {
        s.setVisible(false);
        s.setLabelVisible(false);
      }
      for (Button b : babyCreeperButtonList) {
        b.setVisible(false);
        b.setLabelVisible(false);
      }
      for (Button b : babyCreeperMasterButtonList) {
        b.setVisible(false);
        b.setLabelVisible(false);
      }
    }
    //-----------Use Seeker MakeBabies Button to show and hide the baby seeker controls---------
    if (seekerBaby_toggleUI && masterBehavior_H) {
      for (Slider s : babySeekerSliderList) {
        s.setVisible(true);
        s.setLabelVisible(true);
      }
      for (Button b : babySeekerButtonList) {
        b.setVisible(true);
        b.setLabelVisible(true);
      }
      for (Button b : babySeekerMasterButtonList) {
        b.setVisible(true);
        b.setLabelVisible(true);
      }
    } else {

      for (Slider s : babySeekerSliderList) {
        s.setVisible(false);
        s.setLabelVisible(false);
      }
      for (Button b : babySeekerButtonList) {
        b.setVisible(false);
        b.setLabelVisible(false);
      }
      for (Button b : babySeekerMasterButtonList) {
        b.setVisible(false);
        b.setLabelVisible(false);
      }
    }
    //-----------Use Creeper Seeker Button to show and hide the seeker controls---------
    if (seeker_toggleUI) {
      for (Slider s : seekerSliderList) {
        s.setVisible(true);
        s.setLabelVisible(true);
      }
      for (Button b : seekerButtonList) {
        b.setVisible(true);
        b.setLabelVisible(true);
      }
    } else {

      for (Slider s : seekerSliderList) {
        s.setVisible(false);
        s.setLabelVisible(false);
      }
      for (Button b : seekerButtonList) {
        b.setVisible(false);
        b.setLabelVisible(false);
      }
    }
    color creeper_inactiveCol = color(120, 120, 120, 120);
    color creeper_activeCol = color(0, 255, 0);
    if (D2) {
      this.dim2.setColorBackground(color(120, 255, 0));
    } else {
      this.dim2.setColorBackground(creeper_inactiveCol);
    }
    if (D3) {
      this.dim3.setColorBackground(color(120, 255, 0));
    } else {
      this.dim3.setColorBackground(creeper_inactiveCol);
    }
    if (D2) {
      btypeA.setVisible(false);
      btypeB.setVisible(false);
      btypeC.setVisible(false);
      btypeD.setVisible(false);

      btypeK.setVisible(false);
      btypeKK.setVisible(false);
      btypeKKB.setVisible(false);

      baby_btypeA.setVisible(false);
      baby_btypeB.setVisible(false);
      baby_btypeC.setVisible(false);
      baby_btypeD.setVisible(false);

      seeker_btypeA.setVisible(false);
      seeker_btypeB.setVisible(false);
      seeker_btypeC.setVisible(false);
      seeker_btypeD.setVisible(false);

      bseeker_btypeA.setVisible(false);
      bseeker_btypeB.setVisible(false);
      bseeker_btypeC.setVisible(false);
      bseeker_btypeD.setVisible(false);
      
      btypeF.setVisible(false);
    }
  }
  void guiInit() {
    cp5.setAutoDraw(false);
    hint(DISABLE_DEPTH_TEST);
    if (!D2) {
      cams.beginHUD();
    }
    cp5.draw();
    if (!D2) {
      cams.endHUD();
    }
    hint(ENABLE_DEPTH_TEST);
  }
}