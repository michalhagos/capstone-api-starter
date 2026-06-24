package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }
    // Creates a new profile record called automatically when a user registers
    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }
    // Returns the profile for a given user, or null if one doesn't exist yet
    public Profile getByUserId(int userId) {
        return profileRepository.findById(userId).orElse(null);
    }
    // Updates all editable fields on an existing profile and saves it.
    // Returns null if no profile exists for that userId so the controller
    // can return a 404 instead of throwing an unexpected error.
    public Profile update(int userId, Profile profile) {
        Profile existing = profileRepository.findById(userId).orElse(null);

        if (existing == null) {
            return null;
        }
        // The user id comes from the logged-in user, not from the request body.
        existing.setFirstName(profile.getFirstName());
        existing.setLastName(profile.getLastName());
        existing.setPhone(profile.getPhone());
        existing.setEmail(profile.getEmail());
        existing.setAddress(profile.getAddress());
        existing.setCity(profile.getCity());
        existing.setState(profile.getState());
        existing.setZip(profile.getZip());
        return profileRepository.save(existing);
    }
}
