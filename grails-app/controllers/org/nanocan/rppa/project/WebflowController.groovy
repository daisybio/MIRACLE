package org.nanocan.rppa.project


class WebflowController {

    def rppaFlow = {

        overView {
            on('addPlatelayouts').to('plateLayoutState')
            //on('add Slidelayout').to('slideLayoutState')
            //on('add Slides').to('slideState')
            //on('add Spots').to('spotState')
            //on('add Block Shifts').to('blockShiftState')
            //on('do analysis').to('analysisState')
            on('cancel').to('finish')
        }
        plateLayoutState {
            on('cancel').to('finish')
        }
        /*plateLayoutState {
           on('add-more-Platelayouts'){PlateLayout plateLayout ->
           if(plateLayout.hasErrors()) {
           flash.message = "Validation error"
           return error()
           }
           if(!rppaFlow.listOfPlateLayouts)
           rppaFlow.listOfPlateLayouts = new ArrayList<PlateLayout>()
           def listOfPlateLayouts = flow.listOfPlateLayouts
           listOfPlateLayouts.add(plateLayout)
           [:]
           }.to('plateLayoutState')
           //on('add Slidelayout').to('slideLayoutState')
           on('Overview').to('overView')
           //on('add Slides').to('slideState')
           //on('add Spots').to('spotState')
           //on('add Block Shifts').to('blockShiftState')
           //on('do analysis').to('analysisState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           slideLayoutState{
           on('add slides'){SlideLayout slideLayout ->
           if(command.hasErrors()) {
           flash.message = "Validation error"
           return error()
           }
           [:]
           }.to('slideState')
           on('Overview').to('overView')
           on('add Platelayouts').to('plateLayoutState')
           on('add Spots').to('spotState')
           on('add Block Shifts').to('blockShiftState')
           on('do analysis').to('analysisState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           slideState{
           on('add more Slides'){Slide slide ->
           if(command.hasErrors()) {
           flash.message = "Validation error"
           return error()
           }
           if(!rppaFlow.listOfSlides)
           rppaFlow.listOfSlides = new ArrayList<Slide>()
           def listOfSlides = flow.listOfSlides
           listOfSlides.add(slide)
           [:]
           }.to('slideState')
           on('add Spots').to('spotState')
           on('Overview').to('overView')
           on('add Platelayouts').to('plateLayoutState')
           on('add Block Shifts').to('blockShiftState')
           on('do analysis').to('analysisState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           spotState{
           on('add more Spots'){Spot spot ->
           if(command.hasErrors()) {
           flash.message = "Validation error"
           return error()
           }
           if(!rppaFlow.listOfSpots)
           rppaFlow.listOfSpots = new ArrayList<Spot>()
           def listOfSpots = flow.listOfSlides
           listOfSlides.add(spot)
           [:]
           }.to('spotState')
           on('add Block Shifts').to('blockShiftState')
           on('Overview').to('overView')
           on('add Platelayouts').to('plateLayoutState')
           on('add Slides').to('slideState')
           on('do analysis').to('analysisState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           blockShiftState{
           on('add more Block Shifts'){BlockShift blockShift ->
           if(command.hasErrors()) {
           flash.message = "Validation error"
           return error()
           }
           if(!rppaFlow.listOfBlockShifts)
           rppaFlow.listOfBlockShifts = new ArrayList<BlockShift>()
           def listOfBlockShifts = flow.listOfBlockShifts
           blockShift.add(blockShift)
           [:]
           }.to('blockShiftState')
           on('do analysis').to('analysisState')
           on('Overview').to('overView')
           on('add Platelayouts').to('plateLayoutState')
           on('add Slides').to('slideState')
           on('add Spots').to('spotState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           analysisState{
           on('more analysis').to('analysisState')
           on('finish').to('complete')
           on('Overview').to('overView')
           on('add Platelayouts').to('plateLayoutState')
           on('add Slides').to('slideState')
           on('add Spots').to('spotState')
           on('add Block Shifts').to('blockShiftState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           complete {
           on('save local copy of work for later resumption').to('saveState')
           on('cancel').to('finish')
           on(Exception).to('error')
           }
           saveState{
           on('cancel').to('finish')
           on(Exception).to('error')
           }	*/
        finish {
            redirect(controller: 'webflow')
        }
        error {
            on('confirm').to('finish')
        }
    }
}
