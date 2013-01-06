package org.tomcurran.logbook.util;

import org.tomcurran.logbook.provider.LogbookContract.Aircrafts;
import org.tomcurran.logbook.provider.LogbookContract.Equipment;
import org.tomcurran.logbook.provider.LogbookContract.Jumps;
import org.tomcurran.logbook.provider.LogbookContract.Places;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.text.format.Time;

public class TestData {

    private ContentResolver mResolver;

    private static final PlaceInfo[] PLACES = {
            new PlaceInfo("Strathallan"),
            new PlaceInfo("Black Knights"),
            new PlaceInfo("Wild Geese"),
            new PlaceInfo("Perris"),
            new PlaceInfo("Hibalstow"),
            new PlaceInfo("Prostejov"),
            new PlaceInfo("Sibson"),
            new PlaceInfo("Chatteris")
    };

    private static final AircraftInfo[] AIRCRAFTS = {
            new AircraftInfo("C206"),
            new AircraftInfo("Porter"),
            new AircraftInfo("Caravan"),
            new AircraftInfo("Twin Otter"),
            new AircraftInfo("Skyvan"),
            new AircraftInfo("Balloon"),
            new AircraftInfo("Dorneir"),
            new AircraftInfo("Finist"),
            new AircraftInfo("Let"),
            new AircraftInfo("MI-8")
    };

    private static final EquipmentInfo[] EQUIPMENT = {
            new EquipmentInfo("Manta",       280),
            new EquipmentInfo("Maveron",     240),
            new EquipmentInfo("PD",          210),
            new EquipmentInfo("Balance",     210),
            new EquipmentInfo("Fury",        220),
            new EquipmentInfo("Spectre",     190),
            new EquipmentInfo("Triathalon",  175),
            new EquipmentInfo("Sabre 2",     190),
            new EquipmentInfo("Quadra Vtec", 190),
            new EquipmentInfo("Sabre 1",     170),
            new EquipmentInfo("Sabre 2",     150),
            new EquipmentInfo("Sabre 1",     190)
    };

    private static final ContentValues[] JUMPS = {
            newJump(  0,     0,  0, 1970,  1,  1, "USED TO MATCH INDEXS TO JUMP NUMBER delete"),
            newJump(  1,  3500,  0, 2009, 11, 28, "Good exit, position & count\n(just get head further back)"),
            newJump(  2,  3500,  0, 2010,  2, 21, "Good exit, position & count\nBOC DP's next"),
            newJump(  3,  3500,  0, 2010,  3, 13, "Good setup, turned 90 right on exit, did get DP"),
            newJump(  4,  3500,  0, 2010,  3, 14, "Good setup, exit good & on heading. Fumbled D/P handle but got it on 5 seconds"),
            newJump(  5,  3500,  0, 2010,  3, 21, "Exit OK but didn't arch\nFumbled D/P but when got it good grasp, pull & recovery"),
            newJump(  6,  3500,  0, 2010,  3, 28, "Turned slight to right on eixt. Upper body good but dropped knees\nGood grasp, pull & recovery"),
            newJump(  7,  3500,  0, 2010,  4,  9, "G.A.T.W"),
            newJump(  8,  3500,  0, 2010,  4,  9, "Very good D.P. + ?\nNo ?"),
            newJump(  9,  3500,  0, 2010,  4,  9, "Great D/P.\nJust slight flat.\nC/C O.K.\nJust arch more!"),
            newJump( 10,  3500,  0, 2010,  4, 10, "Excellent D/P.\nJust scccchloooon down!\nGood C/C.\nCleared for F/F."),
            newJump( 11,  3500,  0, 2010,  4, 11, "Perfect D.R.C.P"),
            newJump( 12,  3500,  0, 2010,  4, 12, "Perfect D/P\nThat's all I've got to say about that!"),
            newJump( 13,  3500,  0, 2010,  5,  3, "Good exit into stable spread + arch, V.G reach, pull + recovery\nOn heading through out\nGATW\n3rd good DRCP + cleared for 3 second delay"),
            newJump( 14,  4200,  3, 2010,  5,  4, "Lost exit\nOn back on pull\nLook forward and up on exit\nTry 3 sec again"),
            newJump( 15,  4200,  3, 2010,  5,  8, "Perfect exit & deployment\nWell done"),
            newJump( 16,  4200,  3, 2010,  5,  8, "V.G.A.T.W\n5 sec next\nWell done"),
            newJump( 17,  4200,  5, 2010,  5,  8, "Perfect 5s delay\n/v.V.V.V.V.G.A.T.W\n10s next!"),
            newJump( 18,  4500, 10, 2010,  5,  8, "Perfect 10! V.V.V.V.G.A.T.W\n1 more!"),
            newJump( 19,  4500, 10, 2010,  5,  9, "And again...\nPerfect 10!!\n15s next!"),
            newJump( 20,  6000, 15, 2010,  5,  9, "Excellent 15 sec delay\n15 sec + alti next"),
            newJump( 21,  6000, 15, 2010,  5,  9, "Good exit & fallaway.\nSaw student check alti on way down. Good count\nPull on alti next!\nWell done!"),
            newJump( 22,  5500, 15, 2010,  5, 16, "Perfect straight delay.\nExcellet altitude awareness.\nTurns next!"),
            newJump( 23, 10000, 35, 2010,  5, 16, "Excellent exit, fallaway, right & left 360 turns\nGood alti awareness\nUnstable exit next"),
            newJump( 24,  9000, 30, 2010,  5, 22, "Very unstable exit!\nExcellent arch to recover after 5 seconds\nExcellent alti awareness, pull, recovery + canopy control.\nBackloops nexy"),
            newJump( 25,  8500, 25, 2010,  5, 23, "V. good exit & fallaway\n2 excellent bacloops\nGood alti awareness & good deployment at correct height + good c/c\nDive exit next"),
            newJump( 26, 10000, 35, 2010,  5, 30, "Good position in door & good dive except didn't duck legs up enough. Went into very steep & flipped over but otherise very controlled & stable throughout.\nGood canopy control - just watch flare\nDive exit + track on next jump"),
            newJump( 27, 10000, 35, 2010,  5, 31, "Perfect dive exit!\nExcellent heading control, and altitude awareness.\n2 very steep tracks.\n Godd pull recovery\nExcellent c/c.\nGo into track slower, to gain forward movement.\nTrack again next"),
            newJump( 28, 10000, 35, 2010,  5, 31, "Good exit\n2 excellent tracks\nExcellent A/A, pull + recovery\nTrack turns next!"),
            newJump( 29, 12500, 50, 2010,  8,  4, "Excellent exit! 2 excellent track turns to left + right!\nExcellent A/A, pull + c/c\nCat 8 next!"),
            newJump( 30,  8000, 25, 2010,  8,  4, "Exit from 8000ft due to cloud.\nAll manoeuvres completed, good throughout, and good alti awareness.\nWell done"),
            newJump( 31,  8000, 25, 2010,  8,  5, "Student exit, followed out by instructor. Right 360 turn, left 360 turn.\nUnder canopy did CH1 exercises extending range & flat turns"),
            newJump( 32,  6000, 15, 2010,  8,  7, "Student exit. Straight 10 second delay. Throwaway pilot chute deployed OK. Conversion complete."),
            newJump( 33, 10000, 35, 2010,  8,  7, "Dive exit. Tracked twice but went into both tracks too quickly and lost bit of stability. Need to go into tracks more slowly."),
            newJump( 34, 13000, 60, 2010,  8, 17, "Dive exit. Backloops. 360 turns left & right"),
            newJump( 35, 13000, 60, 2010,  8, 18, "Dive exit. Backloops. 360 turns left & right"),
            newJump( 36, 13000, 60, 2010,  8, 19, "Good exit. Good neutral body position. Good response to signals.\nTwo good forward movements.\nPoints to improve: alti awareness - check alti before & after every manouvre!!\nfast & slow fall next"),
            newJump( 37,  7800, 29, 2010, 10,  2, "Dive eixt. 3 backloops"),
            newJump( 38,  2500,  3, 2010, 10, 10, "hop'n'pop"),
            newJump( 39,  4580, 15, 2010, 10, 16, "goggles weren't tight enough and slipped up my head a bit, so straight fell most of jump after sorting them out"),
            newJump( 40,  4820, 16, 2010, 10, 16, "exit was bad, spent majority of jump getting stable"),
            newJump( 41,  4860, 17, 2010, 10, 23, "Jump started off well with KB making a quick  ascent to altitude. Did a dive exit and was pleased to spot the people that exited before. Then did two backloops before deploying. Terrible deployment which hurt quite a bit, mjor twists which made me quite dizzy. N.B watch for kit packed by McQueen"),
            newJump( 42,  5080, 18, 2010, 11, 27, "Student exit, backloop, freezing cold, landed in pit"),
            newJump( 43,  4860, 15, 2011,  1, 22, "Dived out, chilled out. Played about under canopy. Landed close to pit"),
            newJump( 44,  6230, 22, 2011,  1, 23, "Dive exit, backloop, short track. Didn't land as close to pit as I would have liked"),
            newJump( 45,  4860, 15, 2011,  1, 23, "Dive exit. Concentrated on what my body position was like in freefall"),
            newJump( 46,  4500, 11, 2011,  1, 29, "Dive eixt. Chilled, watched jumpers before hand. Landed in pit"),
            newJump( 47,  3210,  7, 2011,  1, 29, "Dive exit. Relaxed jump. Landed a bit in front of the pit. Don't always use same approach as jump earlier in the day. Always land in or before the pit"),
            newJump( 48,  4900, 18, 2011,  2,  5, "Dive exit. Backloop. Landed good bit away from pit"),
            newJump( 49,  4370, 16, 2011,  2, 12, "Dive exit. Some turns. Followed person in front most of the way under canopy. Landed close to pit"),
            newJump( 50,  8910, 34, 2011,  2, 26, "Dive exit. Did few 360 turns as quick as possible trying to stop controlled on heading. Played with risers for first time."),
            newJump( 51,  7000, 24, 2011,  2, 26, "FS: Fast & Slow fall.\n- good exit from rear dive position\n- good slow fall 'in place'\n- good fast fall although slight increased in seperation\n- good A/A & deployment\nPoints for improvement: tracking -  make slower transition & track less steeply\n360 turns next"),
            newJump( 52, 10000, 35, 2011,  2, 27, "FS: 360 turns\n- good exit (AFF student position)\n- 1st turn wasn't in place - too much arm input\n- 2nd turn much better\nneed to make sure arm & leg inputs are symmetrical\nrepeat jump"),
            newJump( 53,  3140,  8, 2011,  3, 26, "hop'n'pop\nextended range of canopy with rear risers\nturns with rear risers"),
            newJump( 54,  4270, 13, 2011,  3, 26, "hop'n'pop\nextended range of canopy with rear risers\nturns with rear risers"),
            newJump( 55,  4990, 18, 2011,  3, 26, "Student exit. Backloop.\nextended range of canopy with rear risers\nturns with rear risers"),
            newJump( 56,  2500,  3, 2011,  3, 26, "Dive exit. Went quite unstable. Relaxed into rest of jump"),
            newJump( 57,  9480, 38, 2011,  3, 27, "Dive exit. Practice a couple of turns in place and backloops.\nPulled high to play under canopy"),
            newJump( 58, 10240, 44, 2011,  3, 27, "Student exit.\nShort track, practice turn in place, short track again"),
            newJump( 59,  9730, 45, 2011,  3, 27, "FS: 360 turns\nExit: Good float-dive exit from float position. Push off more to improve\nF/F: Right 360 turn OK but fall rate & proximity flucuating. Bring knees closer together & hips down to improve body position.\nBreak-off: Broke off @ 4, rather than 5 as planned. Good track. Deployed at 3, rather than 3.5. Be super aware of altitude.\nNext: repeat"),
            newJump( 60, 12520, 57, 2011,  4,  1, "FS: 360 & 180 turns\nGood exit from float position\ngood 360 turn left - not completed in place. excellent 360 turn right.\ngood 180 turns in both directions\ngood AA & track\nremember - follow pattern & land into wind"),
            newJump( 61, 12700, 59, 2011,  4,  1, "FS: 90 turns & side sliding\ngood exit from dive position\nfirst 3 attempts weren't great, too much arm input not enough leg.\nnext 2 attempts were much better. good AA, track & deployment"),
            newJump( 62, 12460, 55, 2011,  4,  1, "FS: Swoop to pin\nexit & transition to swoop wasn't perfect, recovered and adapted good swoop position\ngood controlled approach\npractised 180 turns\ngood jump"),
            newJump( 63, 12680, 59, 2011,  4,  2, "FS: 5 pt 2 way\nGood level, exit perfect. Got 6 pts easy. Well done. 3 pt 3 way next"),
            newJump( 64, 12690, 59, 2011,  4,  2, "FS: 3 pt 3 way\ngot 3 points - a little bit scrappy. suit is too fast - work on levels. 4 pt 3 way next"),
            newJump( 65, 12700, 58, 2011,  4,  2, "FS: 4 pt 3 way\ngood exit. only managed 1 point, need to improve levels. Proximity, AA, tracking all good"),
            newJump( 66, 12740, 58, 2011,  4,  2, "FS: 4 pt 3 way\ngood exit, only managed 2 points. still work on levels. Proximity, AA, tracking all good"),
            newJump( 67, 12520, 55, 2011,  4,  2, "2 way fs\nwith john, random instructor filming, got seven points"),
            newJump( 68,  5260, 19, 2011,  4,  3, "spun about the place, hit foot on door wich was quite sore. note to self, get foot all the way out the door"),
            newJump( 69,  6890, 25, 2011,  4,  4, "jumped straight off into a backloop\ntracked towards intended landing area away from high ground"),
            newJump( 70, 12770, 61, 2011,  4,  4, "FS: 4 pt 3 way: new suit, much better, 7 points. 4 pt 4 way next"),
            newJump( 71, 12850, 59, 2011,  4,  4, "2 way fs\nwith scott. both hung off skyvan door. didn't hang very long as scott let go very quickly. scott filmed on back, dropped below, dove towards him. did that a few times"),
            newJump( 72, 12690, 60, 2011,  4,  4, "FS: 4 pt 4 way\nbad exit, 1 point. loast AA - focused too much on points. repeat"),
            newJump( 73, 12650, 60, 2011,  4,  5, "FS: 4 pt 4 way\nbad exit, 1 point. experienced jumpers fault"),
            newJump( 74, 12780, 59, 2011,  4,  5, "FS: 4 pt 4 way\nbad exit, 1 point. experienced jumpers fault"),
            newJump( 75, 12460, 59, 2011,  4,  5, "FS: 4 pt 4 way\ngood exit. formation broke, managed 2 points. good flying"),
            newJump( 76, 12640, 59, 2011,  4,  5, "FS: 4 pt 4 way\ngood exit. managed 3 points. repeat\n- check levels\n- only move within your own quadrant"),
            newJump( 77, 12850, 60, 2011,  4,  5, "FS: 4 pt 4 way\ngood exit. went wobbly transitioning to the second point went low then never recovered to everyone else. great flying up to second transition.\nremember: levels first, then slot, then dock"),
            newJump( 78, 13200, 62, 2011,  4,  9, "FS: 4 pt 4 way\nstairstep diamond exit. good transition to star then snowflake, then zipper. did 4 good points well done FS1!"),
            newJump( 79, 12620, 52, 2011,  4,  9, "3 way fs\nwith john & scott\nhorney gorilla exit with john then created a base with scott for john to hybrid"),
            newJump( 80, 12880, 60, 2011,  4,  9, "4 way fs\nwith john, scott, nick\ngreg filmed unlinked exit in a row along skyvan door, then dive after eachother to create a star"),
            newJump( 81, 12560, 63, 2011,  4, 10, "3 way tracking\nwith john & scott\nseperation varied"),
            newJump( 82, 12370, 59, 2011,  4, 10, "2 way fs\nwith dave\nme float, him dive. we both spun really fast was quite difficult to stop cause of momentum. then he went in to sit holding my arms to fling me away"),
            newJump( 83, 12470, 57, 2011,  4, 10, "5 way fs\nwith greg, john, scott, dave\ngreg on inflatable dolphin, me & john holding sides to attempt to keep him stable. very unstable lots of spinning"),
            newJump( 84, 12660, 61, 2011,  4, 10, "dive exit, backlooped, spun really fast one way, tried to stop quick as possible, then same other way. tracked away from no one"),
            newJump( 85, 12700, 59, 2011,  4, 11, "2 way fs\nwith nick\ndid backloops and docks\nsome backloops were bit unstable, others much better"),
            newJump( 86, 12640, 58, 2011,  4, 11, "solo practiced backloop. declared landing"),
            newJump( 87, 12780, 59, 2011,  4, 11, "2 way fs\nrodeoed john. worked for few seconds then I slipped to the left which ended up putting it into a mad spin. declared landing"),
            newJump( 88, 12690, 61, 2011,  4, 11, "2 way fs\nwith john\n aliwyn filming round about us. got bunch of points then played rock, paper, scissors before tracking away. declared landing. increaed range & turns on risers"),
            newJump( 89, 13920, 64, 2011,  4, 11, "2 way fs\nwith john\nswooped out after him while he was on his back going very fast, quite difficult to get down to him. declared landing. increased range & turns on risers"),
            newJump( 90, 12740, 58, 2011,  4, 12, "declared landing. increased range & turns on risers"),
            newJump( 91, 12680, 55, 2011,  4, 12, "5 way trackings\nwith bcpa'ers\nexit went well. found slot but was bit high. good jump"),
            newJump( 92, 18720, 90, 2011,  4, 14, "3 way fs\nwith john & scott\nfun jump"),
            newJump( 93, 12820, 59, 2011,  4, 12, "3 way fs\nwith james & friend\nexit flew upside down but recovered quickly. got next two points before seperation got best of us"),
            newJump( 94, 12450, 58, 2011,  4, 12, "exited in reverse with arms by side to go into a flip. tried going as steep as possible. went well, never flipped, was very steep"),
            newJump( 95, 12590, 59, 2011,  4, 13, "2 way fs\nwith scott\nfloated with scott holding my legs\ntried flipping each other about"),
            newJump( 96, 12320, 56, 2011,  4, 13, "did lots of 360 turns as quick as possible with backloops in between"),
            newJump( 97, 12760, 59, 2011,  4, 13, "4 way fs\nwith scott, john, rich\nexit went very badly, built together and 3 points"),
            newJump( 98,  4710, 16, 2011,  4, 24, "sick last few days so did streaight flat fall and relaxed (after not getting enough altitude to perform a carnage of a 5 way, thank god)"),
            newJump( 99,  4160,  8, 2011,  4, 25, "chilled out"),
            newJump(100,  8060, 30, 2011,  5,  1, "few barrol rolls & backloops & turns\npuled high to try out new canopy\nlots of fun\nlanded in pit"),
            newJump(101,  9570, 40, 2011,  5,  1, "same as #100\nlanded hanger side of pit\nmake sure not to"),
            newJump(102,  5030, 18, 2011,  5,  1, "practice jump master & spotting\nspun about\nlanded in pit"),
            newJump(103,  9040, 38, 2011,  5,  1, "tracked"),
            newJump(104,  4070, 12, 2011,  5,  1, "rolled out plane"),
            newJump(105,  7360, 28, 2011,  5, 30, "good party night before so just relaxed\nmajor chill out, backloop for good measure\ntried different ways of turning"),
            newJump(106,  9820, 43, 2011,  5, 30, "two way with kelly\nneed to make better use of deep brakes if far out on deployment"),
            newJump(107,  2130,  7, 2011,  6,  4, "hop'n'pop"),
            newJump(108,  2400,  7, 2011,  6,  4, "hop'n'pop"),
            newJump(109,  4030, 11, 2011,  6,  4, "hop'n'pop"),
            newJump(110,  2370,  7, 2011,  6,  4, "hop'n'pop"),
            newJump(111,  2850,  8, 2011,  6,  5, "hop'n'pop"),
            newJump(112,  4090, 14, 2011,  6,  5, "hop'n'pop"),
            newJump(113,  4670, 16, 2011,  6, 25, "backloop that didn't go so well\nthen turned to check out others in freefall"),
            newJump(114,  3880, 12, 2011,  6, 25, "backloop exit turned into a barrel roll"),
            newJump(115,  9810, 47, 2011,  6, 25, "5 way speed star unlinked exit with john, sandy, jonny, kieran"),
            newJump(116,  2200,  5, 2011,  6, 26, "hop'n'pop"),
            newJump(117,  4870, 18, 2011,  7,  2, "3 way. Lost grips on exit"),
            newJump(118,  9590, 43, 2011,  7,  3, "2 way with sandy.\nDave filming"),
            newJump(119,  9960, 46, 2011,  7,  3, "3 way with sandy & gus. Got first two points good. Then gus forgot the third point and it sort of went tits up"),
            newJump(120,  9380, 44, 2011,  7,  3, "5 way with sandy, kelly, gus, steve"),
            newJump(121,  8980, 40, 2011,  7, 23, "spotting practical. Tracked"),
            newJump(122,  9400, 40, 2011,  7, 23, "3 way fs with sandy, gus. First two points good, then sandy got away from us"),
            newJump(123,  9550, 40, 2011,  7, 23, "4 way tracking dive with sandy, gus, steve. Gus went wrong way, rest of us stayed in the same vacinity"),
            newJump(124,  9880, 45, 2011,  7, 30, "4 way fs with alan, jo, ian\nBuilt star, broke apart, then build star again"),
            newJump(125,  4710, 16, 2011,  7, 30, "spotted, backloop"),
            newJump(126,  5000,  3, 2011,  8,  1, "first crew, two good docks, two spirals"),
            newJump(127,  6000,  3, 2011,  8,  2, "2 way crew, receiving"),
            newJump(128,  7000,  3, 2011,  8,  3, "3 way crew, docking third"),
            newJump(129,  7000,  3, 2011,  8,  3, "4 way crew, docking fourth"),
            newJump(130,  10000, 3, 2011,  8,  3, "9 way crew attempt\nDocked second, built 4 stack, paul got off top, piloted for the rest of the jump, mangage to get 6 in stack"),
            newJump(131,  10000, 3, 2011,  8,  3, "8 way crew attempt\nDocked third\nManaged to get 7 in the stack"),
            newJump(132,  7000,  3, 2011,  8,  4, "6 way crew attempt"),
            newJump(133,  7000,  3, 2011,  8,  4, "6 way crew attempt, clouds"),
            newJump(134, 11200, 54, 2011,  8,  5, "Carols 3 point 3 way, phil coaching\n2 points, really close to last point"),
            newJump(135,  8000,  3, 2011,  8,  5, "8 way crew\nDocked fourth"),
            newJump(136, 11360, 53, 2011,  8,  5, "Carols second 3 point 3 way, phil coaching\n5 points"),
            newJump(137, 10000,  3, 2011,  8,  5, "8 way crew\nDocked fourth instead of fifth because people weren't filling their slots"),
            newJump(138, 12500, 60, 2011,  8,  5, "Carols 4 point 3 way, greg coach\n4 points"),
            newJump(139, 13680, 60, 2011,  8,  5, "9 way tracking\nMega fun\nWas slightly behind"),
            newJump(140, 10140, 47, 2011,  8,  6, "Carols 4 way attempt"),
            newJump(141, 10360, 49, 2011,  8,  6, "Carols second 4 way attempt. Exit funnelled"),
            newJump(142, 12930, 59, 2011,  9,  3, "2 way fs with john\nUnlinked exit, john floating, me diving\nDid alternate 90, 180, 360 turns and backloop"),
            newJump(143, 13110, 59, 2011,  9,  3, "2 way fs with john\nUnlinked exit, john diving, me floating\nSpun each other about\nTried back tracking"),
            newJump(144,  5000, 16, 2011,  9, 17, "reverse back loop exit, small turns"),
            newJump(145,  8000, 30, 2011,  9, 24, "2 way tracking with sandy\nraced a long side\nlooped around each other"),
            newJump(146,  2100,  5, 2011,  9, 25, "hop'n'pop"),
            newJump(147,  3310,  9, 2011, 10,  2, "reverse back loop exit"),
            newJump(148,  2570,  6, 2011, 10, 30, "stable exits are now the preferred method of removing ones self from an aircraft"),
            newJump(149,  9500, 41, 2011, 11,  5, "2 way fs with john\nhim floating, me sitting\npracticed bcpa 3 way points without third person"),
            newJump(150, 10000, 47, 2011, 11,  5, "4 way fs\nAntonis got 4 points and FS1!"),
            newJump(151, 10000, 42, 2011, 11,  6, "5 way fs\nunlinked speed star\nfour in, john sank"),
            newJump(152, 10500, 50, 2011, 11,  6, "4 way fs\nMike got 6 points and FS1!"),
            newJump(153, 10500,  5, 2011, 11,  6, "4 way fs\ngreat exit, lovely canopy ride"),
            newJump(154, 15200, 75, 2011, 11, 13, "7 way fs\nspeed star built quickly\nbit wobbly, fell apart and rebuilt couple of times"),
            newJump(155, 14900, 70, 2011, 11, 13, "9 way fs\nfloated on back step with john\bbuilt 6 star"),
            newJump(156,  8400, 33, 2011, 11, 19, "tracking\nspiralled, back tracked"),
            newJump(157,  9800, 41, 2011, 12, 10, "tracking\nwith sandy"),
            newJump(158,  4700, 14, 2011, 12, 11, "backlooped"),
            newJump(159,  9700, 39, 2011, 12, 17, "3 way\nwith kelly & caesar for his 300th"),
            newJump(160,  5000, 18, 2011, 12, 18, "2 way\nwith john"),
            newJump(161,  7800, 32, 2011, 12, 18, "3 way fs\nwith chris & santa john\nspun around in star"),
            newJump(162,  5000, 18, 2012,  1,  7, "2 way fs\nwith scott swooping down"),
            newJump(163,  2700,  6, 2012,  1, 14, "hop'n'pop"),
            newJump(164,  2900,  8, 2012,  1, 14, "hop'n'pop"),
            newJump(165,  2700,  4, 2012,  1, 14, "4 way crew\nwith steve, gus, scott"),
            newJump(166,  7400, 29, 2012,  1, 15, "2 way fs\nwith danks"),
            newJump(167,  3100,  6, 2012,  1, 15, "hop'n'pop"),
            newJump(168, 10000, 45, 2012,  1, 22, "4 way fs\nwith sandy, gus, mike\nme in gus base\nsandy jumped on to rodeo"),
            newJump(169, 10400, 46, 2012,  1, 22, "3 way fs\nwith sandy & mike\n360s"),
            newJump(170, 10800, 53, 2012,  1, 22, "3 way tracking\nwith sandy & gus"),
            newJump(171,  5000, 15, 2012,  1, 28, "backloops"),
            newJump(172,  9700, 40, 2012,  1, 28, "2 way fs\nwith mike, lots of turns"),
            newJump(173, 10100, 44, 2012,  1, 28, "3 way fs\nstewards 3 way, 2 points"),
            newJump(174,  5000, 17, 2012,  1, 29, "5 way fs\njonny, john, gus, scott"),
            newJump(175,  8200, 37, 2012,  2,  5, "3 way fs\nwith sandy & carol"),
            newJump(176,  9900, 48, 2012,  2,  5, "3 way fs\nwith sandy & mike"),
            newJump(177,  5300, 20, 2012,  2,  5, "3 way fs\nwith sandy & mike\nexit worked with sandy floating on CN"),
            newJump(178, 11500, 52, 2012,  2, 12, "3 way fs\nstewards 3 point 3 way attempt"),
            newJump(179,  9700, 45, 2012,  2, 12, "3 way fs\nwith jonny & antonis, john on camera\nBCPA strathclyde february"),
            newJump(180,  8800, 38, 2012,  3,  4, "2 way tracking\nwith antonis"),
            newJump(181,  5200, 16, 2012,  3,  4, "tried sit"),
            newJump(182,  3100,  7, 2012,  3, 10, "hop'n'pop"),
            newJump(183,  3000,  7, 2012,  3, 10, "hop'n'pop"),
            newJump(184,  2800,  8, 2012,  3, 10, "hop'n'pop"),
            newJump(185,  4100, 13, 2012,  3, 10, "2 way fs\nwith sandy, double dive\ntracked away"),
            newJump(186,  4000, 12, 2012,  3, 10, "2 way fs\nwith sandy, double dive\ntracked away"),
            newJump(187, 10000, 41, 2012,  3, 11, "attempted free fly"),
            newJump(188,  9800, 47, 2012,  3, 11, "4 way fs\njames FS1"),
            newJump(189, 10000, 46, 2012,  3, 17, "3 way fs\nwith sandy & carol\ntits up from exit"),
            newJump(190,  9600, 45, 2012,  3, 24, "3 way fs\nwith john & antonis\nBCPA strathclyde 3 way march\n3 points"),
            newJump(191,  2300,  4, 2012,  3, 25, "hop'n'pop"),
            newJump(192,  4500, 13, 2012,  6,  2, "hop'n'pop"),
            newJump(193,  5100, 18, 2012,  6,  2, "2 way fs\nwith mike\nsit dive, out, track away"),
            newJump(194,  9600, 45, 2012,  6,  2, "3 way fs\nwith dave & carol\nunlinked exit, dive, total zoo"),
            newJump(195,  6000,  3, 2012,  6,  2, "2 way cf\nwith gus\ntook while to get back into it. got dock on gus, then gus docked on me"),
            newJump(196,  5000,  4, 2012,  6,  2, "2 way cf\nwith sandy\ndocked once"),
            newJump(197,  5500, 17, 2012,  6,  3, "2 way fs\nwith caesar\nhe floated, I held his shorts. he went on his back"),
            newJump(198,  7300, 31, 2012,  6,  3, "2 way tracking\nwith mike\ntracked along side"),
            newJump(199,  6000,  3, 2012,  6,  3, "3 way cf\nwith sandy & greg\npiloted with sandy second, greg third. went into pendulum and downplane soon after"),
            newJump(200,  4500,  3, 2012,  6, 17, "hop'n'pop"),
            newJump(201,  3500,  5, 2012,  6, 17, "hop'n'pop"),
            newJump(202, 13000, 61, 2012,  6, 18, "2 way fs\nwith sandy\nflew about as he tried out his camera"),
            newJump(203, 13000, 53, 2012,  6, 19, "3 way hybrid\ngreg on me & sandys cheststraps\nmark flying about us"),
            newJump(204, 13000, 49, 2012,  6, 19, "4 way fs\ndouble rodeo\nme on paul, beiber on greg"),
            newJump(205, 12700, 54, 2012,  6, 19, "2 way tracking\nwith sandy\nstill can't track on back"),
            newJump(206, 12900, 62, 2012,  6, 19, "6 way tracking\ngreg needs to learn left from right on back\ndocked on him"),
            newJump(207, 13100, 52, 2012,  6, 20, "2 way ff\nwith greg\nback flew ok"),
            newJump(208, 13700, 56, 2012,  6, 20, "solo ff\nstarted to get sit"),
            newJump(209, 13280, 50, 2012,  6, 20, "solo ff\nmuch more confident sit"),
            newJump(210, 13000, 66, 2012,  6, 21, "9 way fs\nstar, zipper, turns"),
            newJump(211, 13700, 58, 2012,  6, 21, "6 way hybrid\nzooo"),
            newJump(212, 12700, 58, 2012,  6, 21, "6 way fs"),
            newJump(213, 13500, 66, 2012,  6, 22, "8 way fs\nturning blocks, to star"),
            newJump(214, 14330, 63, 2012,  7,  7, "3 way fs\nwith john & lucy"),
            newJump(215, 10480, 48, 2012,  7, 21, "4 way fs\nhib cup round 1"),
            newJump(216, 10240, 46, 2012,  7, 21, "4 way fs\nhib cup round 2"),
            newJump(217, 10330, 47, 2012,  7, 21, "4 way fs\nhib cup round 3"),
            newJump(218, 10330, 47, 2012,  7, 21, "4 way fs\nhib cup round 4"),
            newJump(219, 10490, 46, 2012,  7, 21, "4 way fs\nhib cup round 5"),
            newJump(220, 10460, 47, 2012,  7, 21, "4 way fs\nhib cup round 6"),
            newJump(221, 14870, 74, 2012,  7, 21, "12 way fs\n3 points"),
            newJump(222, 14620, 73, 2012,  7, 22, "12 way fs"),
            newJump(223, 15040, 73, 2012,  7, 10, "3 way fs"),
            newJump(224, 10500, 48, 2012,  8, 11, "4 way fs\nBPA nationals round 1\nBLE\n3 points"),
            newJump(225, 10550, 48, 2012,  8, 11, "4 way fs\nBPA nationals round 2\nMFO\n2 points"),
            newJump(226, 10410, 45, 2012,  8, 11, "4 way fs\nBPA nationals round 3\nKAJ\n0 points"),
            newJump(227, 10720, 48, 2012,  8, 11, "4 way fs\nBPA nationals round 4\nQCP\n4 points"),
            newJump(228, 10540, 49, 2012,  8, 12, "4 way fs\nBPA nationals round 5\nGHN\n3 points"),
            newJump(229, 10440, 49, 2012,  8, 12, "4 way fs\nBPA nationals round 6\nJCO\n4 points"),
            newJump(230, 10590, 50, 2012,  8, 12, "4 way fs\nBPA nationals round 7\nLMD\n5 points"),
            newJump(231, 10620, 46, 2012,  8, 12, "4 way fs\nBPA nationals round 8\nPHQ\n6 points"),
            newJump(232,  7050,  3, 2012,  8, 25, "3 way cf\nwith sandy & greg\npendulum to down-plane attempt"),
            newJump(233,  6000,  3, 2012,  8, 25, "3 way cf\nwith sandy & greg\npendulum to down-plane attempt"),
            newJump(234,  5000,  3, 2012,  8, 25, "3 way cf\nwith sandy & greg\npendulum to down-plane attempt"),
            newJump(235,  2230,  3, 2012,  8, 26, "2 way cf\nwith greg\ndownplane"),
            newJump(236,  4000,  3, 2012,  8, 26, "3 way cf\nwith sandy & greg\nptri-plane attempt"),
            newJump(237,  5000,  3, 2012,  8, 26, "3 way cf\nwith sandy & greg\nptri-plane attempt"),
            newJump(238, 12900, 56, 2012,  9,  8, "solo"),
            newJump(239,  9880, 41, 2012,  9, 22, "solo ff"),
            newJump(240,  9460, 41, 2012,  9, 22, "3 way fs\nolwyns 3 way, sandy coaching"),
            newJump(241,  9730, 40, 2012, 10, 13, "3 way fs\nwith carol & anna\njim filming"),
            newJump(242,  6120, 20, 2012, 11,  4, "solo ff\nsit good"),
            newJump(243,  2500,  3, 2013,  1,  5, "hop'n'pop")
    };

    private static ContentValues newJump(int number, int altitude, int delay, int jYear, int jMonth, int jDay, String description) {
            ContentValues values = new ContentValues();
            values.put(Jumps.PLACE_ID, 0);
            values.put(Jumps.AIRCRAFT_ID, 0);
            values.put(Jumps.EQUIPMENT_ID, 0);
            values.put(Jumps.JUMP_NUMBER, number);
            Time time = new Time();
            time.set(jDay, jMonth - 1, jYear);
            values.put(Jumps.JUMP_DATE, time.toMillis(false));
            values.put(Jumps.JUMP_ALTITUDE, altitude);
            values.put(Jumps.JUMP_DELAY, delay);
            values.put(Jumps.JUMP_DESCRIPTION, description);
            return values;
    }

    private static void setJump(ContentValues values, Uri placeUri, Uri aircraftUri, Uri equipmentUri) {
        values.put(Jumps.PLACE_ID, Integer.valueOf(Places.getPlaceId(placeUri)));
        values.put(Jumps.AIRCRAFT_ID, Integer.valueOf(Aircrafts.getAircraftId(aircraftUri)));
        values.put(Jumps.EQUIPMENT_ID, Integer.valueOf(Equipment.getEquipmentId(equipmentUri)));
    }

    public void insert() {
        ContentResolver res = mResolver;

        Uri SPC    = res.insert(Places.CONTENT_URI, PLACES[0].getContentValues());
        Uri BKPC   = res.insert(Places.CONTENT_URI, PLACES[1].getContentValues());
        Uri Geese  = res.insert(Places.CONTENT_URI, PLACES[2].getContentValues());
        Uri Perris = res.insert(Places.CONTENT_URI, PLACES[3].getContentValues());
        Uri Hib    = res.insert(Places.CONTENT_URI, PLACES[4].getContentValues());
        Uri Prost  = res.insert(Places.CONTENT_URI, PLACES[5].getContentValues());
        Uri Sibson = res.insert(Places.CONTENT_URI, PLACES[6].getContentValues());
        Uri Chatrs = res.insert(Places.CONTENT_URI, PLACES[7].getContentValues());

        Uri C206   = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[0].getContentValues());
        Uri Porter = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[1].getContentValues());
        Uri C208   = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[2].getContentValues());
        Uri TwinOt = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[3].getContentValues());
        Uri Skyvan = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[4].getContentValues());
        Uri Baloon = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[5].getContentValues());
        Uri Dornr  = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[6].getContentValues());
        Uri Finist = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[7].getContentValues());
        Uri Let    = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[8].getContentValues());
        Uri MI8    = res.insert(Aircrafts.CONTENT_URI, AIRCRAFTS[9].getContentValues());

        Uri Manta   = res.insert(Equipment.CONTENT_URI, EQUIPMENT[0].getContentValues());
        Uri Maveron = res.insert(Equipment.CONTENT_URI, EQUIPMENT[1].getContentValues());
        Uri PD      = res.insert(Equipment.CONTENT_URI, EQUIPMENT[2].getContentValues());
        Uri Balance = res.insert(Equipment.CONTENT_URI, EQUIPMENT[3].getContentValues());
        Uri Fury    = res.insert(Equipment.CONTENT_URI, EQUIPMENT[4].getContentValues());
        Uri Spectre = res.insert(Equipment.CONTENT_URI, EQUIPMENT[5].getContentValues());
        Uri Triatha = res.insert(Equipment.CONTENT_URI, EQUIPMENT[6].getContentValues());
        Uri Sbr2190 = res.insert(Equipment.CONTENT_URI, EQUIPMENT[7].getContentValues());
        Uri Quadra  = res.insert(Equipment.CONTENT_URI, EQUIPMENT[8].getContentValues());
        Uri Sbr1170 = res.insert(Equipment.CONTENT_URI, EQUIPMENT[9].getContentValues());
        Uri Sbr2150 = res.insert(Equipment.CONTENT_URI, EQUIPMENT[10].getContentValues());
        Uri Sbr1190 = res.insert(Equipment.CONTENT_URI, EQUIPMENT[11].getContentValues());

        setJump(JUMPS[1  ], SPC,    C206,   Manta);
        setJump(JUMPS[2  ], SPC,    C206,   Manta);
        setJump(JUMPS[3  ], SPC,    C206,   Manta);
        setJump(JUMPS[4  ], SPC,    C206,   Manta);
        setJump(JUMPS[5  ], SPC,    C206,   Manta);
        setJump(JUMPS[6  ], SPC,    C206,   Manta);
        setJump(JUMPS[7  ], SPC,    C206,   Manta);
        setJump(JUMPS[8  ], SPC,    C206,   Manta);
        setJump(JUMPS[9  ], SPC,    C206,   Manta);
        setJump(JUMPS[10 ], SPC,    C206,   Manta);
        setJump(JUMPS[11 ], SPC,    C206,   Manta);
        setJump(JUMPS[12 ], SPC,    C206,   Manta);
        setJump(JUMPS[13 ], BKPC,   Porter, Manta);
        setJump(JUMPS[14 ], BKPC,   Porter, Manta);
        setJump(JUMPS[15 ], SPC,    C206,   Manta);
        setJump(JUMPS[16 ], SPC,    C206,   Manta);
        setJump(JUMPS[17 ], SPC,    C206,   Manta);
        setJump(JUMPS[18 ], SPC,    C206,   Manta);
        setJump(JUMPS[19 ], SPC,    C206,   Manta);
        setJump(JUMPS[20 ], SPC,    C206,   Manta);
        setJump(JUMPS[21 ], SPC,    C206,   Manta);
        setJump(JUMPS[22 ], SPC,    C206,   Manta);
        setJump(JUMPS[23 ], SPC,    C206,   Manta);
        setJump(JUMPS[24 ], SPC,    C206,   Manta);
        setJump(JUMPS[25 ], SPC,    C206,   Manta);
        setJump(JUMPS[26 ], SPC,    C206,   Manta);
        setJump(JUMPS[27 ], SPC,    C206,   Manta);
        setJump(JUMPS[28 ], SPC,    C206,   Manta);
        setJump(JUMPS[29 ], SPC,    Porter, Manta);
        setJump(JUMPS[30 ], SPC,    C206,   Manta);
        setJump(JUMPS[31 ], SPC,    C206,   Manta);
        setJump(JUMPS[32 ], SPC,    C206,   Manta);
        setJump(JUMPS[33 ], SPC,    C206,   Manta);
        setJump(JUMPS[34 ], Geese,  C208,   Maveron);
        setJump(JUMPS[35 ], Geese,  C208,   Maveron);
        setJump(JUMPS[36 ], Geese,  C208,   PD);
        setJump(JUMPS[37 ], SPC,    C206,   Manta);
        setJump(JUMPS[38 ], SPC,    C206,   Manta);
        setJump(JUMPS[39 ], SPC,    C206,   Manta);
        setJump(JUMPS[40 ], SPC,    C206,   Manta);
        setJump(JUMPS[41 ], SPC,    C206,   Manta);
        setJump(JUMPS[42 ], SPC,    C206,   Manta);
        setJump(JUMPS[43 ], SPC,    C206,   Manta);
        setJump(JUMPS[44 ], SPC,    C206,   Manta);
        setJump(JUMPS[45 ], SPC,    C206,   Manta);
        setJump(JUMPS[46 ], SPC,    C206,   Manta);
        setJump(JUMPS[47 ], SPC,    C206,   Manta);
        setJump(JUMPS[48 ], SPC,    C206,   Manta);
        setJump(JUMPS[49 ], SPC,    C206,   Manta);
        setJump(JUMPS[50 ], SPC,    C206,   Manta);
        setJump(JUMPS[51 ], SPC,    C206,   Manta);
        setJump(JUMPS[52 ], SPC,    C206,   Manta);
        setJump(JUMPS[53 ], SPC,    C206,   Balance);
        setJump(JUMPS[54 ], SPC,    C206,   Balance);
        setJump(JUMPS[55 ], SPC,    C206,   Fury);
        setJump(JUMPS[56 ], SPC,    C206,   Balance);
        setJump(JUMPS[57 ], SPC,    C206,   Balance);
        setJump(JUMPS[58 ], SPC,    C206,   Balance);
        setJump(JUMPS[59 ], SPC,    C206,   Balance);
        setJump(JUMPS[60 ], Perris, TwinOt, Balance);
        setJump(JUMPS[61 ], Perris, TwinOt, Balance);
        setJump(JUMPS[62 ], Perris, TwinOt, Balance);
        setJump(JUMPS[63 ], Perris, TwinOt, Balance);
        setJump(JUMPS[64 ], Perris, Skyvan, Balance);
        setJump(JUMPS[65 ], Perris, Skyvan, Balance);
        setJump(JUMPS[66 ], Perris, TwinOt, Balance);
        setJump(JUMPS[67 ], Perris, TwinOt, Balance);
        setJump(JUMPS[68 ], Perris, TwinOt, Balance);
        setJump(JUMPS[69 ], Perris, Baloon, Balance);
        setJump(JUMPS[70 ], Perris, TwinOt, Balance);
        setJump(JUMPS[71 ], Perris, Skyvan, Balance);
        setJump(JUMPS[72 ], Perris, TwinOt, Balance);
        setJump(JUMPS[73 ], Perris, TwinOt, Balance);
        setJump(JUMPS[74 ], Perris, TwinOt, Balance);
        setJump(JUMPS[75 ], Perris, TwinOt, Balance);
        setJump(JUMPS[76 ], Perris, TwinOt, Balance);
        setJump(JUMPS[77 ], Perris, TwinOt, Balance);
        setJump(JUMPS[78 ], Perris, TwinOt, Balance);
        setJump(JUMPS[79 ], Perris, Skyvan, Balance);
        setJump(JUMPS[80 ], Perris, Skyvan, Balance);
        setJump(JUMPS[81 ], Perris, Skyvan, Balance);
        setJump(JUMPS[82 ], Perris, TwinOt, Balance);
        setJump(JUMPS[83 ], Perris, Skyvan, Balance);
        setJump(JUMPS[84 ], Perris, TwinOt, Balance);
        setJump(JUMPS[85 ], Perris, TwinOt, Balance);
        setJump(JUMPS[86 ], Perris, Skyvan, Balance);
        setJump(JUMPS[87 ], Perris, Skyvan, Balance);
        setJump(JUMPS[88 ], Perris, TwinOt, Balance);
        setJump(JUMPS[89 ], Perris, TwinOt, Balance);
        setJump(JUMPS[90 ], Perris, TwinOt, Balance);
        setJump(JUMPS[91 ], Perris, Skyvan, Balance);
        setJump(JUMPS[92 ], Perris, Skyvan, Balance);
        setJump(JUMPS[93 ], Perris, TwinOt, Balance);
        setJump(JUMPS[94 ], Perris, TwinOt, Balance);
        setJump(JUMPS[95 ], Perris, TwinOt, Balance);
        setJump(JUMPS[96 ], Perris, TwinOt, Balance);
        setJump(JUMPS[97 ], Perris, TwinOt, Balance);
        setJump(JUMPS[98 ], SPC,    C206,   Balance);
        setJump(JUMPS[99 ], SPC,    C206,   Fury);
        setJump(JUMPS[100], SPC,    C206,   Spectre);
        setJump(JUMPS[101], SPC,    C206,   Spectre);
        setJump(JUMPS[102], SPC,    C206,   Spectre);
        setJump(JUMPS[103], SPC,    C206,   Spectre);
        setJump(JUMPS[104], SPC,    C206,   Spectre);
        setJump(JUMPS[105], SPC,    C206,   Spectre);
        setJump(JUMPS[106], SPC,    C206,   Spectre);
        setJump(JUMPS[107], SPC,    C206,   Spectre);
        setJump(JUMPS[108], SPC,    C206,   Spectre);
        setJump(JUMPS[109], SPC,    C206,   Spectre);
        setJump(JUMPS[110], SPC,    C206,   Spectre);
        setJump(JUMPS[111], SPC,    C206,   Spectre);
        setJump(JUMPS[112], SPC,    C206,   Spectre);
        setJump(JUMPS[113], SPC,    C206,   Spectre);
        setJump(JUMPS[114], SPC,    C206,   Spectre);
        setJump(JUMPS[115], SPC,    C206,   Spectre);
        setJump(JUMPS[116], SPC,    C206,   Spectre);
        setJump(JUMPS[117], SPC,    C206,   Spectre);
        setJump(JUMPS[118], SPC,    C206,   Spectre);
        setJump(JUMPS[119], SPC,    C206,   Spectre);
        setJump(JUMPS[120], SPC,    C206,   Spectre);
        setJump(JUMPS[121], SPC,    C206,   Spectre);
        setJump(JUMPS[122], SPC,    C206,   Spectre);
        setJump(JUMPS[123], SPC,    C206,   Spectre);
        setJump(JUMPS[124], SPC,    C206,   Spectre);
        setJump(JUMPS[125], SPC,    C206,   Spectre);
        setJump(JUMPS[126], SPC,    Porter, Triatha);
        setJump(JUMPS[127], SPC,    Porter, Triatha);
        setJump(JUMPS[128], SPC,    Porter, Triatha);
        setJump(JUMPS[129], SPC,    Porter, Triatha);
        setJump(JUMPS[130], SPC,    Porter, Triatha);
        setJump(JUMPS[131], SPC,    Porter, Triatha);
        setJump(JUMPS[132], SPC,    Porter, Triatha);
        setJump(JUMPS[133], SPC,    Porter, Triatha);
        setJump(JUMPS[134], SPC,    Porter, Spectre);
        setJump(JUMPS[135], SPC,    Porter, Triatha);
        setJump(JUMPS[136], SPC,    Porter, Spectre);
        setJump(JUMPS[137], SPC,    Porter, Triatha);
        setJump(JUMPS[138], SPC,    Porter, Spectre);
        setJump(JUMPS[139], SPC,    Porter, Spectre);
        setJump(JUMPS[139], SPC,    Porter, Spectre);
        setJump(JUMPS[140], SPC,    C206,   Spectre);
        setJump(JUMPS[141], SPC,    C206,   Spectre);
        setJump(JUMPS[142], Geese,  C208,   Sbr2190);
        setJump(JUMPS[143], Geese,  C208,   Sbr2190);
        setJump(JUMPS[144], SPC,    C206,   Balance);
        setJump(JUMPS[145], SPC,    C206,   Spectre);
        setJump(JUMPS[146], SPC,    C206,   Quadra);
        setJump(JUMPS[147], SPC,    C206,   Spectre);
        setJump(JUMPS[148], SPC,    C206,   Spectre);
        setJump(JUMPS[149], SPC,    C206,   Spectre);
        setJump(JUMPS[150], SPC,    C206,   Spectre);
        setJump(JUMPS[151], SPC,    C206,   Balance);
        setJump(JUMPS[152], SPC,    C206,   Spectre);
        setJump(JUMPS[153], SPC,    C206,   Spectre);
        setJump(JUMPS[154], Hib,    Dornr,  Spectre);
        setJump(JUMPS[155], Hib,    Finist, Spectre);
        setJump(JUMPS[156], SPC,    C206,   Fury);
        setJump(JUMPS[157], SPC,    C206,   Fury);
        setJump(JUMPS[158], SPC,    C206,   Spectre);
        setJump(JUMPS[159], SPC,    C206,   Fury);
        setJump(JUMPS[160], SPC,    C206,   Spectre);
        setJump(JUMPS[161], SPC,    C206,   Balance);
        setJump(JUMPS[162], SPC,    C206,   Spectre);
        setJump(JUMPS[163], SPC,    C206,   Spectre);
        setJump(JUMPS[164], SPC,    C206,   Spectre);
        setJump(JUMPS[165], SPC,    C206,   Balance);
        setJump(JUMPS[166], SPC,    C206,   Spectre);
        setJump(JUMPS[167], SPC,    C206,   Spectre);
        setJump(JUMPS[168], SPC,    C206,   Spectre);
        setJump(JUMPS[169], SPC,    C206,   Spectre);
        setJump(JUMPS[170], SPC,    C206,   Spectre);
        setJump(JUMPS[171], SPC,    C206,   Spectre);
        setJump(JUMPS[172], SPC,    C206,   Balance);
        setJump(JUMPS[173], SPC,    C206,   Spectre);
        setJump(JUMPS[174], SPC,    C206,   Spectre);
        setJump(JUMPS[175], SPC,    C206,   Spectre);
        setJump(JUMPS[176], SPC,    C206,   Spectre);
        setJump(JUMPS[177], SPC,    C206,   Spectre);
        setJump(JUMPS[178], SPC,    C206,   Spectre);
        setJump(JUMPS[179], SPC,    C206,   Spectre);
        setJump(JUMPS[180], SPC,    C206,   Spectre);
        setJump(JUMPS[181], SPC,    C206,   Spectre);
        setJump(JUMPS[182], SPC,    C206,   Spectre);
        setJump(JUMPS[183], SPC,    C206,   Quadra);
        setJump(JUMPS[184], SPC,    C206,   Quadra);
        setJump(JUMPS[185], SPC,    C206,   Quadra);
        setJump(JUMPS[186], SPC,    C206,   Quadra);
        setJump(JUMPS[187], SPC,    C206,   Quadra);
        setJump(JUMPS[188], SPC,    C206,   Balance);
        setJump(JUMPS[189], SPC,    C206,   Spectre);
        setJump(JUMPS[190], SPC,    C206,   Spectre);
        setJump(JUMPS[191], SPC,    C206,   Spectre);
        setJump(JUMPS[192], SPC,    C206,   Spectre);
        setJump(JUMPS[193], SPC,    C206,   Spectre);
        setJump(JUMPS[194], SPC,    C206,   Spectre);
        setJump(JUMPS[195], SPC,    C206,   Triatha);
        setJump(JUMPS[196], SPC,    C206,   Spectre);
        setJump(JUMPS[197], SPC,    C206,   Spectre);
        setJump(JUMPS[198], SPC,    C206,   Spectre);
        setJump(JUMPS[199], SPC,    C206,   Triatha);
        setJump(JUMPS[200], SPC,    C206,   Sbr1170);
        setJump(JUMPS[201], SPC,    C206,   Sbr1170);
        setJump(JUMPS[202], Prost,  Let,    Sbr1170);
        setJump(JUMPS[203], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[204], Prost,  C208,   Sbr1170);
        setJump(JUMPS[205], Prost,  Dornr,  Sbr1170);
        setJump(JUMPS[206], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[207], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[208], Prost,  C208,   Sbr1170);
        setJump(JUMPS[209], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[210], Prost,  Let,    Sbr1170);
        setJump(JUMPS[211], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[212], Prost,  MI8,    Sbr1170);
        setJump(JUMPS[213], Prost,  Let,    Sbr1170);
        setJump(JUMPS[214], Sibson, C208,   Sbr1170);
        setJump(JUMPS[215], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[216], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[217], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[218], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[219], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[220], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[221], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[222], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[223], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[224], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[225], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[226], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[227], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[228], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[229], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[230], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[231], Hib,    Dornr,  Sbr1170);
        setJump(JUMPS[232], SPC,    C206,   Triatha);
        setJump(JUMPS[233], SPC,    C206,   Triatha);
        setJump(JUMPS[234], SPC,    C206,   Triatha);
        setJump(JUMPS[235], SPC,    C206,   Triatha);
        setJump(JUMPS[236], SPC,    C206,   Triatha);
        setJump(JUMPS[237], SPC,    C206,   Triatha);
        setJump(JUMPS[238], Chatrs, TwinOt, Sbr1190);
        setJump(JUMPS[239], SPC,    C206,   Sbr2150);
        setJump(JUMPS[240], SPC,    C206,   Sbr1190);
        setJump(JUMPS[241], SPC,    C206,   Quadra);
        setJump(JUMPS[242], SPC,    C206,   Sbr2150);
        setJump(JUMPS[243], SPC,    C206,   Sbr2150);

        res.bulkInsert(Jumps.CONTENT_URI, JUMPS);
    }

    public TestData(ContentResolver resolver) {
        mResolver = resolver;
    }

    public void delete() {
        ContentResolver resolver = mResolver;
        resolver.delete(Jumps.CONTENT_URI, null, null);
        resolver.delete(Places.CONTENT_URI, null, null);
        resolver.delete(Aircrafts.CONTENT_URI, null, null);
        resolver.delete(Equipment.CONTENT_URI, null, null);
    }

    private static class PlaceInfo {

        ContentValues values;
        
        public PlaceInfo(String name) {
            values = new ContentValues();
            values.put(Places.PLACE_NAME, name);
        }
        
        public ContentValues getContentValues() {
            return values;
        }
    }

    private static class AircraftInfo {

        ContentValues values;
        
        public AircraftInfo(String name) {
            values = new ContentValues();
            values.put(Aircrafts.AIRCRAFT_NAME, name);
        }
        
        public ContentValues getContentValues() {
            return values;
        }
    }

    private static class EquipmentInfo {

        ContentValues values;

        public EquipmentInfo(String name, int size) {
            values = new ContentValues();
            values.put(Equipment.EQUIPMENT_CANOPY_NAME, name);
            values.put(Equipment.EQUIPMENT_CANOPY_SIZE, size);
        }
        
        public ContentValues getContentValues() {
            return values;
        }
    }

}